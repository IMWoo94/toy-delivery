package org.delivery.api.domain.user.controller;

import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserFindEmailRequest;
import org.delivery.api.domain.user.controller.model.UserFindPasswordRequest;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserPasswordResponse;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/user")
public class UserOpenApiController {

	private final UserBusiness userBusiness;

	// 사용자 가입 요청
	@PostMapping("/register")
	public Api<UserResponse> register(
		@Valid
		@RequestBody Api<UserRegisterRequest> request
	) {
		var response = userBusiness.register(request.getBody());
		return Api.OK(response);
	}

	// 로그인
	@PostMapping("/login")
	public Api<UserResponse> login(
		@Valid
		@RequestBody Api<UserLoginRequest> request
	) {
		var response = userBusiness.login(request.getBody());
		return Api.OK(response);
	}

	// Email 찾기
	@PostMapping("/findEmail")
	public Api<UserResponse> findEmail(
		@Valid
		@RequestBody Api<UserFindEmailRequest> request
	) {
		var response = userBusiness.findEmail(request.getBody());
		return Api.OK(response);
	}

	// Password 찾기
	@PostMapping("/findPassword")
	public Api<UserPasswordResponse> findPassword(
		@Valid
		@RequestBody Api<UserFindPasswordRequest> request
	) {
		var response = userBusiness.findPassword(request.getBody());
		return Api.OK(response);
	}
}
