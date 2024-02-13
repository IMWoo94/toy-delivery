package org.delivery.storeadmin.domain.user.converter;

import org.delivery.db.store.StoreEntity;
import org.delivery.db.storeuser.StoreUserEntity;
import org.delivery.storeadmin.common.annotation.Converter;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Converter
public class StoreUserConverter {

	public StoreUserEntity toEntity(
		StoreUserRegisterRequest request,
		StoreEntity storeEntity
	) {
		return StoreUserEntity.builder()
			.email(request.getEmail())
			.password(request.getPassword())
			.role(request.getRole())
			.storeId(storeEntity.getId())
			.build();
	}

	public StoreUserResponse toResponse(
		StoreUserEntity storeUserEntity,
		StoreEntity storeEntity
	) {
		return StoreUserResponse.builder()
			.store(
				StoreUserResponse.StoreResponse.builder()
					.id(storeEntity.getId())
					.name(storeEntity.getName())
					.build()
			)
			.user(
				StoreUserResponse.UserResponse.builder()
					.id(storeUserEntity.getId())
					.email(storeUserEntity.getEmail())
					.status(storeUserEntity.getStatus())
					.role(storeUserEntity.getRole())
					.registeredAt(storeUserEntity.getRegisteredAt())
					.unregisteredAt(storeUserEntity.getUnregisteredAt())
					.lastLoginAt(storeUserEntity.getLastLoginAt())
					.build()
			)
			.build();
	}

	public StoreUserResponse toResponse(
		UserSession userSession
	) {
		return StoreUserResponse.builder()
			.store(
				StoreUserResponse.StoreResponse.builder()
					.id(userSession.getStoreId())
					.name(userSession.getStoreName())
					.build()
			)
			.user(
				StoreUserResponse.UserResponse.builder()
					.id(userSession.getUserId())
					.email(userSession.getEmail())
					.status(userSession.getStatus())
					.role(userSession.getRole())
					.registeredAt(userSession.getRegisteredAt())
					.unregisteredAt(userSession.getUnregisteredAt())
					.lastLoginAt(userSession.getLastLoginAt())
					.build()
			)
			.build();
	}
}
