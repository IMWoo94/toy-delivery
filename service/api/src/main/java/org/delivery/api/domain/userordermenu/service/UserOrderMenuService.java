package org.delivery.api.domain.userordermenu.service;

import java.util.List;
import java.util.Optional;

import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.userordermenu.UserOrderMenuEntity;
import org.delivery.db.userordermenu.UserOrderMenuRepository;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserOrderMenuService {

	private final UserOrderMenuRepository userOrderMenuRepository;

	public List<UserOrderMenuEntity> getUserOrderMenu(Long userOrderId) {
		return userOrderMenuRepository.findAllByUserOrderIdAndStatus(userOrderId, UserOrderMenuStatus.REGISTERED);
	}

	public UserOrderMenuEntity order(
		UserOrderMenuEntity userOrderMenuEntity
	) {
		return Optional.ofNullable(userOrderMenuEntity)
			.map(it -> {
				it.setStatus(UserOrderMenuStatus.REGISTERED);
				return userOrderMenuRepository.save(it);
			})
			.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
	}
}
