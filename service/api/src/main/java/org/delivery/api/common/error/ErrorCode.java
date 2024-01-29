package org.delivery.api.common.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs {

	OK(HttpStatus.OK.value(), 200, "성공"),
	BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청"),

	SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버 에러"),

	NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "Null Point");

	// Internal Http 통신의 Status 코드
	private final Integer httpStatusCode;
	// 정의한 status 코드
	private final Integer errorCode;
	private final String description;

}
