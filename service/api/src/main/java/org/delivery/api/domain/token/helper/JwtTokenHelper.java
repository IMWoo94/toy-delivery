package org.delivery.api.domain.token.helper;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import org.delivery.api.common.error.TokenErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.ifs.TokenHelperIfs;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtTokenHelper implements TokenHelperIfs {

	@Value("${token.secret.key}")
	private String secretKey;
	@Value("${token.access-token.plus-hour}")
	private Long accessTokenPlusHour;
	@Value("${token.refresh-token.plus-hour}")
	private Long refreshTokenPlusHour;

	@Override
	public TokenDto issueAccessToken(Map<String, Object> data) {
		// 토큰 유효 시간 확인
		var expiredLocalDateTime = LocalDateTime.now().plusHours(accessTokenPlusHour);
		var expiredAt = Date.from(expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

		// 서명 키 생성
		var key = Keys.hmacShaKeyFor(secretKey.getBytes());

		// Jwt 생성
		var jwtToken = Jwts.builder()
			.signWith(key, SignatureAlgorithm.HS256)
			.setClaims(data)
			.setExpiration(expiredAt)
			.compact();

		return TokenDto.builder()
			.token(jwtToken)
			.expiredAt(expiredLocalDateTime)
			.build();
	}

	@Override
	public TokenDto issueRefreshToken(Map<String, Object> data) {
		// 토큰 유효 시간 확인
		var expiredLocalDateTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);
		var expiredAt = Date.from(expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

		// 서명 키 생성
		var key = Keys.hmacShaKeyFor(secretKey.getBytes());

		// Jwt 생성
		var jwtToken = Jwts.builder()
			.signWith(key, SignatureAlgorithm.HS256)
			.setClaims(data)
			.setExpiration(expiredAt)
			.compact();

		return TokenDto.builder()
			.token(jwtToken)
			.expiredAt(expiredLocalDateTime)
			.build();
	}

	@Override
	public Map<String, Object> validationTokenWithThrow(String token) {
		var key = Keys.hmacShaKeyFor(secretKey.getBytes());

		var parser = Jwts.parserBuilder()
			.setSigningKey(key)
			.build();

		try {
			// 토큰 확인
			var result = parser.parseClaimsJws(token);
			return new HashMap<String, Object>(result.getBody());
		} catch (Exception e) {
			if (e instanceof SignatureException) {
				// 토큰이 유효 하지 않음
				throw new ApiException(TokenErrorCode.INVALID_TOKEN, e);
			} else if (e instanceof ExpiredJwtException) {
				// 토큰 만료
				throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, e);
			} else {
				// 그 외 예외
				throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION, e);
			}
		}
	}
}
