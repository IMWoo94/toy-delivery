package org.delivery.api.domain.user.controller;

import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserApiController {

	private final UserBusiness userBusiness;

	@GetMapping("/me")
	public Api<UserResponse> me(
		@UserSession User user
	) {
		var response = userBusiness.me(user);
		return Api.OK(response);
	}
}
