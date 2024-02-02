package org.delivery.api.common.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

/**
 * Token 의 경우 2000번대 에러코드 사용
 */
@AllArgsConstructor
public enum TokenErrorCode implements ErrorCodeIfs {

	INVALID_TOKEN(HttpStatus.BAD_REQUEST.value(), 2000, "유효 하지 않은 토큰"),
	EXPIRED_TOKEN(HttpStatus.BAD_REQUEST.value(), 2000, "만료된 토큰"),
	TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST.value(), 2002, "토큰 알 수 없는 에러"),
	AUTHORIZATION_TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), 2003, "인증 헤더 토큰 없음.");

	// Internal Http 통신의 Status 코드
	private final Integer httpStatusCode;
	// 정의한 status 코드
	private final Integer errorCode;
	private final String description;

	@Override
	public Integer getHttpStatusCode() {
		return this.httpStatusCode;
	}

	@Override
	public Integer getErrorCode() {
		return this.errorCode;
	}

	@Override
	public String getDescription() {
		return this.description;
	}
}
