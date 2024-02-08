package org.delivery.api.domain.userorder.service;

import java.util.List;

import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserOrderService {

	private final UserOrderRepository userOrderRepository;

	public UserOrderEntity getUserOrderWithThrow(
		Long id,
		Long userId
	) {
		return userOrderRepository.findAllByIdAndStatusAndUserId(id, UserOrderStatus.REGISTERED, userId)
			.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
	}

	public List<UserOrderEntity> getUserOrderList(Long userId) {
		return userOrderRepository.findAllByUserIdAndStatusOrderByIdDesc(userId, UserOrderStatus.REGISTERED);
	}

	// 주문

	// 주문 확인

	// 요리 시작

	// 배달 시작

	// 배달 완료
	
}
