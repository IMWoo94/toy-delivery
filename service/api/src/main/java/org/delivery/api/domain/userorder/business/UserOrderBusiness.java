package org.delivery.api.domain.userorder.business;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.OrderMenu;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import org.delivery.db.userordermenu.UserOrderMenuEntity;

import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class UserOrderBusiness {

	private final UserOrderService userOrderService;
	private final UserOrderConverter userOrderConverter;
	private final StoreMenuService storeMenuService;
	private final StoreMenuConverter storeMenuConverter;
	private final UserOrderMenuConverter userOrderMenuConverter;
	private final UserOrderMenuService userOrderMenuService;
	private final StoreService storeService;
	private final StoreConverter storeConverter;

	/**
	 * 1. 사용자, 메뉴 ID
	 * 2. userOrder 생성
	 * 3. userOrderMenu 생성
	 * 4. 응답 생성
	 */
	public UserOrderResponse userOrder(User user, UserOrderRequest request) {
		// 실제로 가게의 메뉴 인지를 확인 하며, StoreMenuEntity 생성
		var storeMenuEntityList = request.getStoreMenuIdList().stream()
			.map(it -> {
				return storeMenuService.getStoreMenuWithThrow(it.getStoreMenuId());
			})
			.toList();
		// Order Entity 생성
		var userOrderEntity = userOrderConverter.toEntity(user, storeMenuEntityList, request.getStoreMenuIdList());

		// Order Entity 등록
		var newUserOrderEntity = userOrderService.order(userOrderEntity);

		// 주문 + 주문 메뉴의 연결을 하기 위해 UserOrderEntity 를 생성 하여 맵핑
		List<UserOrderMenuEntity> userOrderMenuEntityList = new ArrayList<>();
		storeMenuEntityList
			.forEach(it -> {
				int quantity = request.getStoreMenuIdList()
					.stream()
					.filter(menus -> menus.getStoreMenuId() == it.getId())
					.findFirst()
					.map(OrderMenu::getQuantity)
					.get();
				for (int i = 0; i < quantity; i++) {
					var userOrderMenuEntity = userOrderMenuConverter.toEntity(
						newUserOrderEntity,
						it
					);
					userOrderMenuEntityList.add(userOrderMenuEntity);
				}
			});

		// 맵핑 등록
		userOrderMenuEntityList.forEach(userOrderMenuService::order);

		// 응답 결과 반환
		return userOrderConverter.toResponse(newUserOrderEntity);
	}

	public List<UserOrderDetailResponse> current(User user) {
		// 배달 완료를 제외한 모든 주문 상태의 주문 내역을 조회
		var userOrderEntityList = userOrderService.current(user.getId());

		// 주문 1건씩 처리
		var userOrderDetailResponseList = userOrderEntityList.stream()
			.map(it -> {
				// 사용자가 주문 메뉴
				var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());

				var storeMenuEntityList = userOrderMenuEntityList.stream()
					.map(userOrderMenuEntity -> {
						var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(
							userOrderMenuEntity.getStoreMenuId());
						return storeMenuEntity;
					}).toList();

				// 사용자가 주문한 스토어 TODO 리팩토링 필요
				var storeEntity = storeService.getStoreWithThrow(
					storeMenuEntityList.stream().findFirst().get().getStore().getId());

				return UserOrderDetailResponse.builder()
					.userOrderResponse(userOrderConverter.toResponse(it))
					.storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
					.storeResponse(storeConverter.toResponse(storeEntity))
					.build();
			}).collect(Collectors.toList());

		return userOrderDetailResponseList;
	}

	public List<UserOrderDetailResponse> history(User user) {
		// 배달 완료 주문 내역을 조회
		var userOrderEntityList = userOrderService.history(user.getId());

		// 주문 1건씩 처리
		var userOrderDetailResponseList = userOrderEntityList.stream()
			.map(it -> {
				// 사용자가 주문 메뉴
				var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());

				var storeMenuEntityList = userOrderMenuEntityList.stream()
					.map(userOrderMenuEntity -> {
						var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(
							userOrderMenuEntity.getStoreMenuId());
						return storeMenuEntity;
					}).toList();

				// 사용자가 주문한 스토어 TODO 리팩토링 필요
				var storeEntity = storeService.getStoreWithThrow(
					storeMenuEntityList.stream().findFirst().get().getStore().getId());

				return UserOrderDetailResponse.builder()
					.userOrderResponse(userOrderConverter.toResponse(it))
					.storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
					.storeResponse(storeConverter.toResponse(storeEntity))
					.build();
			}).collect(Collectors.toList());

		return userOrderDetailResponseList;
	}

	public UserOrderDetailResponse read(User user, Long orderId) {
		var userOrderEntity = userOrderService.getUserOrderWithOutStatusWithThrow(orderId, user.getId());

		// 사용자가 주문 메뉴
		var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());

		var storeMenuEntityList = userOrderMenuEntityList.stream()
			.map(userOrderMenuEntity -> {
				var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(
					userOrderMenuEntity.getStoreMenuId());
				return storeMenuEntity;
			}).toList();

		// 사용자가 주문한 스토어 TODO 리팩토링 필요
		var storeEntity = storeService.getStoreWithThrow(
			storeMenuEntityList.stream().findFirst().get().getStore().getId());

		return UserOrderDetailResponse.builder()
			.userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
			.storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
			.storeResponse(storeConverter.toResponse(storeEntity))
			.build();
	}
}
