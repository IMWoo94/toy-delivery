package org.delivery.api.exceptionhandler;

import org.delivery.api.common.api.Api;
import org.delivery.api.common.exception.ApiException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
@Order(value = Integer.MIN_VALUE)
public class ApiExceptionHandler {

	@ExceptionHandler(value = ApiException.class)
	public ResponseEntity<Api<Object>> apiException(ApiException apiException) {
		log.error("ApiExceptionHandler.apiException : ", apiException);

		var errorCode = apiException.getErrorCodeIfs();

		return ResponseEntity
			.status(errorCode.getHttpStatusCode())
			.body(Api.ERROR(errorCode, apiException.getErrorDescription()));
	}
}
