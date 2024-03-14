package org.delivery.batch.settle.days;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SettleDaysJobConfiguration {

	private final JobRepository jobRepository;

	private final PlatformTransactionManager platformTransactionManager;

	@Bean
	public Job settleDaysJob(
		Step settleDaysStep
	) {
		return new JobBuilder("settleDaysJob", jobRepository)
			.incrementer(new RunIdIncrementer())
			.validator(new DefaultJobParametersValidator(new String[] {"targetDate"}, new String[0]))
			.start(settleDaysStep)
			.build();
	}

	@Bean
	public Step settleDaysStep(
		UserOrderEntityJpaPagingItemReader userOrderEntityJpaPagingItemReader
	) {
		return new StepBuilder("settleDaysStep", jobRepository)
			.chunk(1000, platformTransactionManager)
			.reader(userOrderEntityJpaPagingItemReader)
			// .processor()
			.writer(it -> {
				log.info(it.toString());
			})
			.build();
	}
}
