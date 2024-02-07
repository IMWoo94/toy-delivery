package org.delivery.api.common.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

/**
 * Store 의 경우 3000번대 에러코드 사용
 */
@AllArgsConstructor
public enum StoreErrorCode implements ErrorCodeIfs {

	STORE_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), 3404, "스토어를 찾을 수 없음.");

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
