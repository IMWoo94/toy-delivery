package org.delivery.storeadmin.domain.sse.connection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import org.delivery.storeadmin.domain.sse.connection.model.UserSseConnection;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SseConnectionPool implements ConnectionPoolIfs<String, UserSseConnection> {

	private static final Map<String, UserSseConnection> connectionPool = new ConcurrentHashMap<>();

	@Override
	public void addSession(String key, UserSseConnection userSseConnection) {
		connectionPool.put(key, userSseConnection);
	}

	@Override
	public UserSseConnection getSession(String key) {
		return connectionPool.get(key);
	}

	@Override
	public void onCompletionCallback(UserSseConnection userSseConnection) {
		log.info("call back connection pool completion : {}", userSseConnection);
		connectionPool.remove(userSseConnection.getUniqueKey());
	}
}
