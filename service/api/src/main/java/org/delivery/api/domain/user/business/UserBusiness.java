package org.delivery.api.domain.user.business;

import java.util.Optional;

import org.delivery.api.common.annotation.Business;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.controller.model.UserFindEmailRequest;
import org.delivery.api.domain.user.controller.model.UserFindPasswordRequest;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserPasswordResponse;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class UserBusiness {

	private final UserService userService;
	private final UserConverter userConverter;
	private final TokenBusiness tokenBusiness;

	public UserResponse register(UserRegisterRequest request) {
		// var entity = userConverter.toEntity(request);
		// var newEntity = userService.register(entity);
		// var response = userConverter.toResponse(newEntity);

		return Optional.ofNullable(request)
			.map(userConverter::toEntity)
			.map(userService::register)
			.map(userConverter::toResponse)
			.orElseThrow(
				() -> new ApiException(ErrorCode.NULL_POINT, "UserBusiness.register : UserRegisterRequest Null"));
	}

	/**
	 * 1. email, password 를 가지고 사용자 확인
	 * 2. user entity 로그인 확인
	 * 3. token 생성
	 * 4. token response
	 * @param request
	 */
	public TokenResponse login(UserLoginRequest request) {
		var userEntity = userService.getUserWithThrow(request.getEmail(), request.getPassword());
		// token 발행
		return tokenBusiness.issueToken(userEntity);
	}

	public UserResponse findEmail(UserFindEmailRequest request) {
		return Optional.ofNullable(request)
			.map(it -> userService.getUserEmailWithThrow(request.getName(), request.getAddress()))
			.map(userConverter::toResponse)
			.orElseThrow(
				() -> new ApiException(ErrorCode.NULL_POINT, "UserBusiness.findEmail : UserFindEmailRequest Null"));
	}

	public UserPasswordResponse findPassword(UserFindPasswordRequest request) {
		return Optional.ofNullable(request)
			.map(
				it -> userService.getUserPasswordWithThrow(request.getEmail(),
					request.getName(),
					request.getAddress())
			)
			.map(userConverter::toPasswordResponse)
			.orElseThrow(
				() -> new ApiException(ErrorCode.NULL_POINT,
					"UserBusiness.findPassword : UserFindPasswordRequest Null"));
	}
}
