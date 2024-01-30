package org.delivery.api.domain.user.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.user.UserEntity;
import org.delivery.db.user.UserRepository;
import org.delivery.db.user.enums.UserStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * User 도메인 로직을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public UserEntity register(UserEntity userEntity) {
		return Optional.ofNullable(userEntity)
			.map(it -> {
				userEntity.setStatus(UserStatus.REGISTERED);
				userEntity.setRegisteredAt(LocalDateTime.now());
				return userRepository.save(userEntity);
			})
			.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserService.register : UserEntity Null"));
	}
}
