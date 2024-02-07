package org.delivery.api.domain.store.business;

import java.util.List;
import java.util.stream.Collectors;

import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.db.store.enums.StoreCategory;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class StoreBusiness {

	private final StoreService storeService;
	private final StoreConverter storeConverter;

	public StoreResponse register(StoreRegisterRequest request) {
		var entity = storeConverter.toEntity(request);
		var newEnitiy = storeService.register(entity);
		var response = storeConverter.toResponse(newEnitiy);
		return response;
	}

	public List<StoreResponse> searchCategory(StoreCategory storeCategory, Pageable pageable) {

		var storeList = storeService.searchByCategory(storeCategory, pageable);
		return storeList.stream()
			.map(storeConverter::toResponse)
			.collect(Collectors.toList());
	}

	public StoreResponse searchWithThrow(Long storeId) {
		var entity = storeService.getStoreWithThrow(storeId);
		var response = storeConverter.toResponse(entity);
		return response;
	}
}
