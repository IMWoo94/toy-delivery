package org.delivery.api.exceptionhandler;

import org.delivery.api.common.api.Api;
import org.delivery.api.common.error.ErrorCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE) // 가장 마지막에 실행 적용
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Api<Object>> exception(Exception e) {
		log.error("GlobalExceptionHandler.exception : ", e);

		return ResponseEntity
			.status(500)
			.body(
				Api.ERROR(ErrorCode.SERVER_ERROR)
			);
	}
}
