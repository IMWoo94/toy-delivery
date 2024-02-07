package org.delivery.api.domain.storemenu.business;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.delivery.api.common.annotation.Business;
import org.delivery.api.common.error.StoreErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.store.business.StoreBusiness;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuRegisterRequest;
import org.delivery.api.domain.storemenu.controller.model.StoreMenuResponse;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;

import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class StoreMenuBusiness {
	private final StoreMenuService storeMenuService;
	private final StoreMenuConverter storeMenuConverter;
	private final StoreBusiness storeBusiness;
	private final StoreConverter storeConverter;

	public StoreMenuResponse register(
		StoreMenuRegisterRequest request
	) {
		// 가게 정보 확인
		var store = storeBusiness.searchWithThrow(request.getStoreId());

		return Optional.ofNullable(store)
			.map(it -> {
				var entity = storeMenuConverter.toEntity(request);
				// Store Entity Set
				entity.setStore(storeConverter.toEntity(store));
				// Store Menu register
				var newEntity = storeMenuService.register(entity);
				var response = storeMenuConverter.toResponse(newEntity);
				return response;
			})
			.orElseThrow(() -> new ApiException(StoreErrorCode.STORE_NOT_FOUND));
	}

	public List<StoreMenuResponse> search(
		Long storeId
	) {
		var list = storeMenuService.getStoreMenuByStoreId(storeId);
		return list.stream()
			.map(storeMenuConverter::toResponse)
			.collect(Collectors.toList());
	}
}
