package org.smarty.core.support.schedule;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author qul
 * @since LVGG1.1
 */
public abstract class JobRunnable implements Job {

	public abstract void run(JobDataMap dataMap);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		run(context.getMergedJobDataMap());
	}
}
