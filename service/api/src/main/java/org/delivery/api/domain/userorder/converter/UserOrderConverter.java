package org.delivery.api.domain.userorder.converter;

import java.math.BigDecimal;
import java.util.List;

import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.OrderMenu;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;

@Converter
public class UserOrderConverter {

	public UserOrderEntity toEntity(
		User user,
		List<StoreMenuEntity> storeMenuEntityList,
		List<OrderMenu> orderMenus
	) {
		var totalAmount = storeMenuEntityList.stream()
			.map(it -> {
				BigDecimal amount = it.getAmount();
				var quantity = orderMenus.stream()
					.filter(menus -> menus.getStoreMenuId() == it.getId())
					.findFirst()
					.map(t -> new BigDecimal(t.getQuantity()))
					.orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "수량에 문제가 있습니다."));
				return amount.multiply(quantity);
			})
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		return UserOrderEntity.builder()
			.userId(user.getId())
			.amount(totalAmount)
			.build();
	}

	public UserOrderResponse toResponse(
		UserOrderEntity userOrderEntity
	) {
		return UserOrderResponse.builder()
			.id(userOrderEntity.getId())
			.status(userOrderEntity.getStatus())
			.amount(userOrderEntity.getAmount())
			.orderedAt(userOrderEntity.getOrderedAt())
			.acceptedAt(userOrderEntity.getAcceptedAt())
			.deliveryStartedAt(userOrderEntity.getDeliveryStartedAt())
			.cookingStartedAt(userOrderEntity.getCookingStartedAt())
			.build();
	}
}
