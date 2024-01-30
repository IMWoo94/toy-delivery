package org.delivery.api.domain.user.converter;

import java.util.Optional;

import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.db.user.UserEntity;

import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class UserConverter {

	public UserEntity toEntity(UserRegisterRequest userRegisterRequest) {

		return Optional.ofNullable(userRegisterRequest)
			.map(it -> {
				// to Entity

				return UserEntity.builder()
					.name(it.getName())
					.email(it.getEmail())
					.address(it.getAddress())
					.password(it.getPassword())
					.build();
			})
			.orElseThrow(
				() -> new ApiException(ErrorCode.NULL_POINT, "UserConverter.toEntity : UserRegisterRequest Null"));
	}

	public UserResponse toResponse(UserEntity userEntity) {
		return Optional.ofNullable(userEntity)
			.map(it -> {
				// to UserResponse

				return UserResponse.builder()
					.id(userEntity.getId())
					.name(userEntity.getName())
					.status(userEntity.getStatus())
					.email(userEntity.getEmail())
					.address(userEntity.getAddress())
					.registeredAt(userEntity.getRegisteredAt())
					.unregisteredAt(userEntity.getUnregisteredAt())
					.lastLoginAt(userEntity.getLastLoginAt())
					.build();

			})
			.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserConverter.toResponse : UserEntity Null"));
	}
}
