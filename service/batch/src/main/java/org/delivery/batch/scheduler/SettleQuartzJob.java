package org.delivery.batch.scheduler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SettleQuartzJob extends QuartzJobBean {

	private final JobLauncher jobLauncher;
	private final Job settleDaysJob;

	private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	public SettleQuartzJob(JobLauncher jobLauncher, Job settleDaysJob) {
		this.jobLauncher = jobLauncher;
		this.settleDaysJob = settleDaysJob;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		String targetDate = LocalDate.now().format(dateTimeFormatter);
		log.info("SettleQuartzJob executeInternal {}", LocalDate.now());
		JobParameters parameters = new JobParametersBuilder()
			.addString("targetDate", targetDate)
			.toJobParameters();

		try {
			jobLauncher.run(settleDaysJob, parameters);
		} catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException | JobRestartException |
				 JobParametersInvalidException e) {
			throw new RuntimeException(e);
		}
	}
}
