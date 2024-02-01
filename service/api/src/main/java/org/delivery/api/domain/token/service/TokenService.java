package org.delivery.api.domain.token.service;

import java.util.HashMap;
import java.util.Objects;

import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.ifs.TokenHelperIfs;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * token 에 대한 도메인 담당
 */
@Service
@RequiredArgsConstructor
public class TokenService {

	private final TokenHelperIfs tokenHelperIfs;

	public TokenDto issueAccessToken(Long userId) {
		var data = new HashMap<String, Object>();
		data.put("userId", userId);
		return tokenHelperIfs.issueAccessToken(data);
	}

	public TokenDto issueRefreshToken(Long userId) {
		var data = new HashMap<String, Object>();
		data.put("userId", userId);
		return tokenHelperIfs.issueRefreshToken(data);
	}

	public Long validationToken(String token) {
		var map = tokenHelperIfs.validationTokenWithThrow(token);
		var userId = map.get("userId");
		Objects.requireNonNull(userId, () -> {
			throw new ApiException(ErrorCode.NULL_POINT);
		});
		return Long.parseLong(userId.toString());
	}

}
