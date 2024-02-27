package org.delivery.storeadmin.domain.userorder.consumer;

import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.storeadmin.domain.userorder.business.UserOrderBusiness;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserOrderConsumer {

	private final UserOrderBusiness userOrderBusiness;

	@RabbitListener(queues = "delivery.queue")
	public void userOrderConsumer(
		UserOrderMessage userOrderMessage
	) {
		log.info("message queue >> {}", userOrderMessage);
		userOrderBusiness.pushUserOrder(userOrderMessage);
	}
}
