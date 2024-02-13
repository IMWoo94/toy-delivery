package org.delivery.storeadmin.domain.user.business;

import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.storeadmin.common.annotation.Business;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.delivery.storeadmin.domain.user.service.StoreUserService;

import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class StoreUserBusiness {

	private final StoreUserConverter storeUserConverter;
	private final StoreUserService storeUserService;

	// TODO STORE SERVICE 생기면 이관
	private final StoreRepository storeRepository;

	public StoreUserResponse register(StoreUserRegisterRequest request) {

		var storeName = request.getStoreName();
		var storeEntity = storeRepository.findFirstByNameAndStatusOrderByIdDesc(storeName, StoreStatus.REGISTERED);
		var entity = storeUserConverter.toEntity(request, storeEntity.get()); // TODO Null 확인 필요
		var newEntity = storeUserService.register(entity);
		var response = storeUserConverter.toResponse(newEntity, storeEntity.get());

		return response;
	}

}
