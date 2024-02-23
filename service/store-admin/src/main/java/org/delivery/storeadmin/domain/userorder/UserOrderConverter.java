package org.delivery.storeadmin.domain.userorder;

import org.delivery.common.message.model.UserOrderMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserOrderConverter {

	@RabbitListener(queues = "delivery.queue")
	public void userOrderConverter(
		UserOrderMessage userOrderMessage
	) {
		log.info("message queue >> {}", userOrderMessage);
	}
}
