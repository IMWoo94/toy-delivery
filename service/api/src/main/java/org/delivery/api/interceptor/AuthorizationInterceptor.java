package org.delivery.api.interceptor;

import java.util.Objects;

import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.TokenErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

	private final TokenBusiness tokenBusiness;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		// Controller 에 가기 전 거치는 공간
		log.info("AuthorizationInterceptor.preHandle url : {}", request.getRequestURI());

		// WEB, chrome 의 경우 GET, POST OPTIONS 를 통해서 지원 하는지 확인 하는 기능 존재
		// GET, POST OPTIONS -> PASS
		if (HttpMethod.OPTIONS.matches(request.getMethod())) {
			return true;
		}

		// js, html, png resource 를 요청 하는 경우
		// Resource -> PASS
		if (handler instanceof ResourceHttpRequestHandler) {
			return true;
		}

		// TODO header 검증
		var accessToken = request.getHeader("authorization-token");
		if (accessToken == null) {
			throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
		}

		var userId = tokenBusiness.validationAccessToken(accessToken);

		// 인증 성공
		if (userId != null) {
			// RequestContextHolder 요청에 대한 스레드를 ID 로 가지는 ThreadLocal 을 가져올 수 있도록 해준다.
			// 즉, Http 요청 하나 마다 Thread 가 할당되며 해당 Thread 는 ThreadLocal 이라는 고유한 저장공간을 가지는데,
			// 이 ThreadLocal 을 통해서 Http request 정보를 Controller 영역 이외에도 사용할 수 있도록 해준다.
			var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
			requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST);
			return true;
		}

		throw new ApiException(ErrorCode.BAD_REQUEST, "인증 실패");
	}
}
