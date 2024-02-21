package org.delivery.api.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class rabbitMqConfig {

	@Bean
	public DirectExchange directExchange() {
		return new DirectExchange("delivery.exchange");
	}

	@Bean
	public Queue queue() {
		return new Queue("delivery.queue");
	}

	@Bean
	public Binding binding(DirectExchange directExchange, Queue queue) {
		return BindingBuilder.bind(queue).to(directExchange).with("delivery.key");
	}
}
