package org.delivery.api.common.api;

import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.ErrorCodeIfs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

	private Integer resultCode;
	private String resultMessage;
	private String resultDescription;

	public static Result OK() {
		return Result.builder()
			.resultCode(ErrorCode.OK.getErrorCode())
			.resultMessage(ErrorCode.OK.getDescription())
			.resultDescription("성공")
			.build();
	}

	public static Result ERROR(ErrorCodeIfs errorCodeIfs) {
		return Result.builder()
			.resultCode(errorCodeIfs.getErrorCode())
			.resultMessage(errorCodeIfs.getDescription())
			.resultDescription("에러")
			.build();
	}

	public static Result ERROR(ErrorCodeIfs errorCodeIfs, Throwable throwable) {
		return Result.builder()
			.resultCode(errorCodeIfs.getErrorCode())
			.resultMessage(errorCodeIfs.getDescription())
			.resultDescription(throwable.getLocalizedMessage())
			.build();
	}

	public static Result ERROR(ErrorCodeIfs errorCodeIfs, String description) {
		return Result.builder()
			.resultCode(errorCodeIfs.getErrorCode())
			.resultMessage(errorCodeIfs.getDescription())
			.resultDescription(description)
			.build();
	}
}
