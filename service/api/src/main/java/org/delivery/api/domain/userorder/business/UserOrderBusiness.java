package org.delivery.api.domain.userorder.business;

import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;

import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {

	private final UserOrderService userOrderService;
	private final UserOrderConverter userOrderConverter;
	private final StoreMenuService storeMenuService;
	private final UserOrderMenuConverter userOrderMenuConverter;
	private final UserOrderMenuService userOrderMenuService;

	/**
	 * 1. 사용자, 메뉴 ID
	 * 2. userOrder 생성
	 * 3. userOrderMenu 생성
	 * 4. 응답 생성
	 */
	public UserOrderResponse userOrder(User user, UserOrderRequest request) {
		// 실제로 가게의 메뉴 인지를 확인 하며, StoreMenuEntity 생성
		var storeMenuEntityList = request.getStoreMenuIdList().stream()
			.map(storeMenuService::getStoreMenuWithThrow)
			.toList();
		// Order Entity 생성
		var userOrderEntity = userOrderConverter.toEntity(user, storeMenuEntityList);

		// Order Entity 등록
		var newUserOrderEntity = userOrderService.order(userOrderEntity);

		// 주문 + 주문 메뉴의 연결을 하기 위해 UserOrderEntity 를 생성 하여 맵핑
		var userOrderMenuEntityList = storeMenuEntityList.stream()
			.map(it -> {
				// menu + userOrder
				return userOrderMenuConverter.toEntity(
					newUserOrderEntity,
					it
				);
			}).toList();

		// 맵핑 등록
		userOrderMenuEntityList.forEach(userOrderMenuService::order);

		// 응답 결과 반환
		return userOrderConverter.toResponse(newUserOrderEntity);
	}
}
