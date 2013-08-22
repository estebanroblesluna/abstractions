package com.modules.quartz;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.core.api.Startable;
import com.core.api.Terminable;

public class QuartzConnector implements Startable, Terminable {

	private static Log log = LogFactory.getLog(QuartzConnector.class);
	
	private volatile Scheduler scheduler;
	private final Map<JobMessageSource, String> jobNames;
	
	public QuartzConnector() {
		this.jobNames = new ConcurrentHashMap<JobMessageSource, String>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void terminate() {
		if (scheduler != null) {
			try {
				scheduler.shutdown();
			} catch (SchedulerException e) {
				log.warn("Error stopping scheduler");
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			log.warn("Error starting scheduler");
		}
	}

	public void addJob(JobMessageSource source) {
		this.removeJob(source); //make sure we don't have a job for this source
		
		String newName = UUID.randomUUID().toString();

		JobDataMap data = new JobDataMap();
		data.put("source", source);
		
		JobDetail jobDetail = JobBuilder.newJob()
			    .withIdentity(newName, "actions")
			    .setJobData(data)
			    .build();
		
		String cronExpression = source.getCronExpression();
		Trigger trigger = CronScheduleBuilder
				.cronSchedule(cronExpression)
				.build();
		
		try {
			scheduler.scheduleJob(jobDetail, trigger);
			this.jobNames.put(source, newName);
		} catch (SchedulerException e) {
			log.warn("Error scheduling job");
		}
	}

	public void removeJob(JobMessageSource jobMessageSource) {
		String id = this.jobNames.remove(jobMessageSource);
		if (id != null) {
			try {
				scheduler.deleteJob(new JobKey(id, "actions"));
			} catch (SchedulerException e) {
				log.warn("Error deleting job");
			}
		}
	}
}
