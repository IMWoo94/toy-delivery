package org.delivery.storeadmin.domain.user.controller;

import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/store-user")
public class StoreUserApiController {
	private final StoreUserConverter storeUserConverter;

	@GetMapping("/me")
	public StoreUserResponse me(
		@Parameter(hidden = true)
		@AuthenticationPrincipal UserSession userSession
	) {
		// 인증이 된 사용자 @AuthenticationPrincipal 을 통해서 SecurityContext 에 있는 유저 정보를 가져오기
		return storeUserConverter.toResponse(userSession);
	}
}
