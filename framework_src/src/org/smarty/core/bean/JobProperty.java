package org.smarty.core.bean;

import org.smarty.core.support.schedule.JobRunnable;

/**
 * JobData
 */
public class JobProperty {
	private String groupName;
	private long startDelay;
	private Class<? extends JobRunnable> jobRunnable;
	private String description;

	public JobProperty(String groupName, Class<? extends JobRunnable> jobRunnable) {
		this.groupName = groupName;
		this.jobRunnable = jobRunnable;
	}

	public String getGroupName() {
		return groupName;
	}

	public long getStartDelay() {
		return startDelay;
	}

	public void setStartDelay(long startDelay) {
		this.startDelay = startDelay;
	}

	public Class<? extends JobRunnable> getJobRunnable() {
		return jobRunnable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
