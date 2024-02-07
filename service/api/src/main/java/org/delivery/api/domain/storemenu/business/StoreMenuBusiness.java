package org.delivery.api.domain.storemenu.business;

import java.util.List;
import java.util.stream.Collectors;

import org.delivery.api.common.annotation.Business;
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

	public StoreMenuResponse register(
		StoreMenuRegisterRequest request
	) {
		var entity = storeMenuConverter.toEntity(request);
		var newEntity = storeMenuService.register(entity);
		var response = storeMenuConverter.toResponse(newEntity);
		return response;
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
