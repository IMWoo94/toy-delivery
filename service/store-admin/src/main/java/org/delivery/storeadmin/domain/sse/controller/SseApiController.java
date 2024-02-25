package org.delivery.storeadmin.domain.sse.controller;

import java.util.Optional;

import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.sse.connection.model.UserSseConnection;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/sse")
public class SseApiController {

	private final SseConnectionPool sseConnectionPool;
	private final ObjectMapper objectMapper;

	@GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseBodyEmitter connect(
		@Parameter(hidden = true)
		@AuthenticationPrincipal UserSession userSession
	) {
		log.info("login user {}", userSession.toString());

		var userSseConnection = UserSseConnection.connect(
			userSession.getUserId().toString(),
			sseConnectionPool,
			objectMapper
		);

		// connection pool add
		sseConnectionPool.addSession(userSession.getUserId().toString(), userSseConnection);

		return userSseConnection.getSseEmitter();
	}

	@GetMapping("/push-event")
	public void pushEvent(
		@Parameter(hidden = true)
		@AuthenticationPrincipal UserSession userSession
	) {
		var userSseConnection = sseConnectionPool.getSession(userSession.getUserId().toString());

		Optional.ofNullable(userSseConnection)
			.ifPresent(it -> {
					it.sendMessage("hello");
				}
			);
	}
}
