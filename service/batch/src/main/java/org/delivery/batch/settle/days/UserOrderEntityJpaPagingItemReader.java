package org.delivery.batch.settle.days;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Iterator;

import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@StepScope
public class UserOrderEntityJpaPagingItemReader implements ItemReader<UserOrderEntity> {

	private final UserOrderRepository userOrderRepository;

	private String targetDate;

	private int pageNo = 0;

	private Iterator<UserOrderEntity> userOrderEntityIterator;

	public UserOrderEntityJpaPagingItemReader(
		UserOrderRepository userOrderRepository,
		@Value("#{jobParameters['targetDate']}") String targetDate
	) {
		log.info("construct UserOrderEntityJpaPagingItemReader {}", targetDate);
		this.userOrderRepository = userOrderRepository;
		this.targetDate = targetDate;
		this.pageNo = 0;
		this.userOrderEntityIterator = Collections.emptyIterator();
	}

	@Override
	public UserOrderEntity read() throws
		Exception,
		UnexpectedInputException,
		ParseException,
		NonTransientResourceException {
		if (userOrderEntityIterator.hasNext())
			return userOrderEntityIterator.next();

		PageRequest pageRequest = PageRequest.of(pageNo++, 10);
		LocalDateTime currentDate = LocalDate.parse(targetDate, DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay();
		log.info("currentDate {}", currentDate);
		userOrderEntityIterator = userOrderRepository.findAllByOrderedAtOrderByIdDesc(pageRequest, currentDate)
			.iterator();

		if (!userOrderEntityIterator.hasNext())
			return null;

		return userOrderEntityIterator.next();
	}
}
