package org.delivery.batch.settle.days;

import org.delivery.db.settle.SettleDaysEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@StepScope
public class SettleDaysItemProcessor implements ItemProcessor<UserOrderEntity, SettleDaysEntity> {

	@Override
	public SettleDaysEntity process(UserOrderEntity item) throws Exception {
		return null;
	}
}
