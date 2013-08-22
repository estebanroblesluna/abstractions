package com.modules.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJob implements Job {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap data = context.getJobDetail().getJobDataMap();
		if (data.containsKey("source")) {
			JobMessageSource source = (JobMessageSource) data.get("source");
			source.trigger();
		}
	}
}
