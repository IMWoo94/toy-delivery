package org.delivery.api.common.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Producer {

	private final RabbitTemplate rabbitTemplate;

	public void producer(String exchange, String routeKey, Object object) {
		rabbitTemplate.convertAndSend(exchange, routeKey, object);
	}
}