package org.delivery.batch.settle;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SimpleBatchTestConfiguration {

	private final JobRepository jobRepository;

	private final PlatformTransactionManager platformTransactionManager;

	@Bean
	public Job simpleTestJob(
		Step simpleStep
	) {
		return new JobBuilder("simpleTestJob", jobRepository)
			.incrementer(new RunIdIncrementer())
			.start(simpleStep)
			.build();
	}

	@Bean
	public Step simpleStep() {
		return new StepBuilder("simpleStep", jobRepository)
			.tasklet((a, b) -> {
				log.info("simpleStep start");

				return RepeatStatus.FINISHED;
			}, platformTransactionManager)
			.build();
	}
}
