package org.delivery.api.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
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

		// 일단 검증 통과 처리
		return true;
	}
}
