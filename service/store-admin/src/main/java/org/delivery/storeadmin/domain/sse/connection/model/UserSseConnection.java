package org.delivery.storeadmin.domain.sse.connection.model;

import java.io.IOException;

import org.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class UserSseConnection {

	private final String uniqueKey;
	private final SseEmitter sseEmitter;

	private ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs;

	private UserSseConnection(
		String uniqueKey,
		ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs
	) {
		this.uniqueKey = uniqueKey;
		this.connectionPoolIfs = connectionPoolIfs;

		this.sseEmitter = new SseEmitter();

		// onCompletion
		this.sseEmitter.onCompletion(() -> {
			// connection pool remove
			this.connectionPoolIfs.onCompletionCallback(this);
		});

		// onTimeout
		this.sseEmitter.onTimeout(() -> {
			this.sseEmitter.complete();
		});

		// onopen
		this.sendMessage("onopen", "connect");
	}

	public static UserSseConnection connect(
		String uniqueKey,
		ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs
	) {
		return new UserSseConnection(uniqueKey, connectionPoolIfs);
	}

	public void sendMessage(String eventName, Object data) {
		var event = SseEmitter.event()
			.name(eventName)
			.data(data);

		try {
			this.sseEmitter.send(event);
		} catch (IOException e) {
			this.sseEmitter.completeWithError(e);
		}
	}

	public void sendMessage(Object data) {
		var event = SseEmitter.event()
			.data(data);

		try {
			this.sseEmitter.send(event);
		} catch (IOException e) {
			this.sseEmitter.completeWithError(e);
		}
	}
}
