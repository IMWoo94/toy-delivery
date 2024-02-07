package org.delivery.api.domain.store.converter;

import java.util.Optional;

import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.db.store.StoreEntity;

import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class StoreConverter {

	public StoreEntity toEntity(StoreRegisterRequest request) {
		return Optional.ofNullable(request)
			.map(it -> {

				return StoreEntity.builder()
					.name(it.getName())
					.address(it.getAddress())
					.category(it.getStoreCategory())
					.minimumAmount(it.getMinimumAmount())
					.minimumDeliveryAmount(it.getMinimumDeliveryAmount())
					.thumbnailUrl(it.getThumbnailUrl())
					.phoneNumber(it.getPhoneNumber())
					.build();
			})
			.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
	}

	public StoreResponse toResponse(StoreEntity entity) {
		return Optional.ofNullable(entity)
			.map(it -> {

				return StoreResponse.builder()
					.id(entity.getId())
					.name(entity.getName())
					.status(entity.getStatus())
					.category(entity.getCategory())
					.star(entity.getStar())
					.address(entity.getAddress())
					.minimumAmount(entity.getMinimumAmount())
					.minimumDeliveryAmount(entity.getMinimumDeliveryAmount())
					.thumbnailUrl(entity.getThumbnailUrl())
					.phoneNumber(entity.getPhoneNumber())
					.build();
			})
			.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
	}
}
