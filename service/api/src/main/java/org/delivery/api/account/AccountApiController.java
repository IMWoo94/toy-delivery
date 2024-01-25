package org.delivery.api.account;

import org.delivery.db.account.AccountEntity;
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

	@GetMapping
	public void testSave() {
		AccountEntity accountEntity = AccountEntity.builder().build();
		accountRepository.save(accountEntity);
	}
}
