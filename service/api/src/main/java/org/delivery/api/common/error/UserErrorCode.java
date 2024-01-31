package org.delivery.api.common.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

/**
 * User 의 경우 1000번대 에러코드 사용
 */
@AllArgsConstructor
public enum UserErrorCode implements ErrorCodeIfs {

	USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), 1404, "사용자를 찾을 수 없음."),
	USER_DUPLICATION(HttpStatus.BAD_REQUEST.value(), 1405, "중복된 사용자 입니다.");

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
