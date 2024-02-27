package org.delivery.storeadmin.domain.userorder.service;

import java.util.Optional;

import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserOrderService {

	private final UserOrderRepository userOrderRepository;

	public Optional<UserOrderEntity> getUserOrder(Long id) {
		return userOrderRepository.findById(id);
	}
}
