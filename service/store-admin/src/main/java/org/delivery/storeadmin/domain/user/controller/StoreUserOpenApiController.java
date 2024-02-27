package org.delivery.storeadmin.domain.user.controller;

import org.delivery.storeadmin.domain.user.business.StoreUserBusiness;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api/store-user")
public class StoreUserOpenApiController {

	private final StoreUserBusiness storeUserBusiness;

	@PostMapping
	public StoreUserResponse register(
		@Valid
		@RequestBody StoreUserRegisterRequest storeUserRegisterRequest
	) {
		var response = storeUserBusiness.register(storeUserRegisterRequest);
		return response;
	}
}
