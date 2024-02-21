package org.delivery.api.config.rabbitmq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class rabbitMqConfig {

	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange("delivery.exchange");
	}
}
