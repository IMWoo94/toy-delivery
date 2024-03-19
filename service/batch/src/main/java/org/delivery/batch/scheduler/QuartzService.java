package org.delivery.batch.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class QuartzService {

	private final Scheduler scheduler;

	@PostConstruct
	public void init() {
		log.info("QuartzScheduler init");

		JobDetail settleDaysJob = JobBuilder.newJob(SettleQuartzJob.class)
			.withIdentity("settleDaysJob", "settleBatch")
			.build();

		Trigger trigger = TriggerBuilder
			.newTrigger()
			.withSchedule(CronScheduleBuilder.cronSchedule("30 5 24 * * ?"))
			.build();

		try {
			scheduler.scheduleJob(settleDaysJob, trigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
}
