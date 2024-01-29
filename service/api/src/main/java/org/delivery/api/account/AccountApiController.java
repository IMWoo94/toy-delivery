package org.delivery.api.account;

import java.time.LocalDateTime;

import org.delivery.api.account.model.AccountMeResponse;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.db.account.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountApiController {

	private final AccountRepository accountRepository;

	@GetMapping("/me")
	public Api<AccountMeResponse> me() {
		var response = AccountMeResponse.builder()
			.name("홍길동")
			.email("A@gmail.com")
			.registeredAt(LocalDateTime.now())
			.build();

		return Api.OK(response);
	}

	@GetMapping("/error")
	public Api<Object> error() {
		return Api.ERROR(ErrorCode.SERVER_ERROR, "일부러 에러 발생 시키기");
	}
}
