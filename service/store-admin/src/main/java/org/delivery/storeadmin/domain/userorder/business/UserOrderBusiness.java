package org.delivery.storeadmin.domain.userorder.business;

import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
정import org.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.storeadmin.domain.userorder.converter.UserOrderConverter;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.delivery.storeadmin.domain.userordermenu.service.UserOrderMenuService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserOrderBusiness {
	private final UserOrderService userOrderService;
	private final SseConnectionPool sseConnectionPool;
	private final UserOrderMenuService userOrderMenuService;
	private final StoreMenuService storeMenuService;
	private final StoreMenuConverter storeMenuConverter;
	private final UserOrderConverter userOrderConverter;

	/**
	 * 1. 주문
	 * 2. 주문 내역 착기
	 * 3. 가맹점 찾기
	 * 4. 연결된 세션 찾아서 push
	 */
	public void pushUserOrder(UserOrderMessage userOrderMessage) {
		// 2. 주문 내역 찾기
		// user order entity
		var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId())
			.orElseThrow(() -> new RuntimeException("사용자 주문내역 없음"));

		// user order menu
		var userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());

		// user order menu -> store menu
		var storeMenuResponseList = userOrderMenuList.stream()
			.map(it -> {
				return storeMenuService.getStoreMenuWithThrow(it.getStoreMenuId());
			})
			.map(storeMenuConverter::toResponse)
			.toList();

		// response
		var userOrderResponse = userOrderConverter.toResponse(userOrderEntity);

		// push
		var push = UserOrderDetailResponse.builder()
			.userOrderResponse(userOrderResponse)
			.storeMenuResponseList(storeMenuResponseList)
			.build();

		// 가맹점과 연결된 sse connection get
		var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

		// 사용자 ( 가맹점 ) push
		userConnection.sendMessage(push);

	}

}
