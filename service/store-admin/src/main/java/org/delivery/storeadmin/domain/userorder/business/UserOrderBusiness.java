package org.delivery.storeadmin.domain.userorder.business;

import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserOrderBusiness {
	private final UserOrderService userOrderService;
	private final SseConnectionPool sseConnectionPool;

	/**
	 * 1. 주문
	 * 2. 주문 내역 착기
	 * 3. 가맹점 찾기
	 * 4. 연결된 세션 찾아서 push
	 */
	public void pushUserOrder(UserOrderMessage userOrderMessage) {
		// 2. 주문 내역 찾기
		var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId())
			.orElseThrow(() -> new RuntimeException("사용자 주문내역 없음"));

		// user order entity

		// user order menu

		// user order menu -> store menu

		// response

		// push

		// 가맹점과 연결된 sse connection get
		var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

		// 주문 메뉴, 가격, 상태

		// 사용자 ( 가맹점 ) push
		// userConnection.sendMessage();

	}

}
