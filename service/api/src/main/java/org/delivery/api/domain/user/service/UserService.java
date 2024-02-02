package org.delivery.api.domain.user.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.UserErrorCode;
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
				duplicationJoin(userEntity);
				userEntity.setStatus(UserStatus.REGISTERED);
				userEntity.setRegisteredAt(LocalDateTime.now());
				return userRepository.save(userEntity);
			})
			.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserService.register : UserEntity Null"));
	}

	private void duplicationJoin(UserEntity userEntity) {
		var duplication = userRepository.duplicationJoin(userEntity.getEmail(),
			userEntity.getName(),
			userEntity.getAddress(),
			UserStatus.REGISTERED
		).isPresent();

		if (duplication)
			throw new ApiException(UserErrorCode.USER_DUPLICATION, "UserService.duplicationJoin : User Duplication");
	}

	public UserEntity login(
		String email,
		String password
	) {
		return getUserWithThrow(email, password);
	}

	public UserEntity getUserWithThrow(
		String email,
		String password
	) {
		return userRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc(
				email,
				password,
				UserStatus.REGISTERED
			)
			.orElseThrow(
				() -> new ApiException(UserErrorCode.USER_NOT_FOUND, "UserService.getUserWithThrow : User Not Found"));
	}

	public UserEntity getUserWithThrow(Long userId) {
		return userRepository.findFirstByIdAndStatusOrderByIdDesc(
			userId,
			UserStatus.REGISTERED
		).orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
	}

	public UserEntity getUserEmailWithThrow(
		String name,
		String address
	) {
		return userRepository.findFirstByNameAndAddressAndStatusOrderByIdDesc(
				name,
				address,
				UserStatus.REGISTERED
			)
			.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND,
				"UserService.getUserEmailWithThrow : User Not Found"));
	}

	public UserEntity getUserPasswordWithThrow(
		String email,
		String name,
		String address
	) {
		return userRepository.findFirstByEmailAndNameAndAddressAndStatusOrderByIdDesc(
				email,
				name,
				address,
				UserStatus.REGISTERED
			)
			.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND,
				"UserService.getUserPasswordWithThrow : User Not Found"));
	}

}
