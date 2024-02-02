package org.delivery.api.resolver;

import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.user.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

	private final UserService userService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// 지원 하는 파라미터 , 어노테이션 확인

		// 1. 어노테이션이 있는지 확인
		var annotation = parameter.hasParameterAnnotation(UserSession.class);
		// 2. 파라미터 타입 체크
		var parameterType = parameter.getParameterType().equals(User.class);

		return (annotation && parameterType);

	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		// support parameter 에서 지원 시 실행

		// request context 찾아오기
		var requestContext = RequestContextHolder.getRequestAttributes();
		var userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

		var userEntity = userService.getUserWithThrow(Long.parseLong(userId.toString()));

		// 사용자 정보 셋팅
		return User.builder()
			.id(userEntity.getId())
			.name(userEntity.getName())
			.email(userEntity.getEmail())
			.status(userEntity.getStatus())
			.address(userEntity.getAddress())
			.password(userEntity.getPassword())
			.registeredAt(userEntity.getRegisteredAt())
			.unregisteredAt(userEntity.getUnregisteredAt())
			.lastLoginAt(userEntity.getLastLoginAt())
			.build();
	}
}
