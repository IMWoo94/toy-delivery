package org.delivery.api.domain.token.converter;

import java.util.Objects;

import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenConverter {

	public TokenResponse toResponse(
		TokenDto accessToken,
		TokenDto refreshToken
	) {

		Objects.requireNonNull(accessToken, () -> {
			throw new ApiException(ErrorCode.NULL_POINT);
		});
		Objects.requireNonNull(refreshToken, () -> {
			throw new ApiException(ErrorCode.NULL_POINT);
		});

		return TokenResponse.builder()
			.accessToken(accessToken.getToken())
			.accessTokenExpiredAt(accessToken.getExpiredAt())
			.refreshToken(refreshToken.getToken())
			.refreshTokenExpiredAt(refreshToken.getExpiredAt())
			.build();
	}

}
