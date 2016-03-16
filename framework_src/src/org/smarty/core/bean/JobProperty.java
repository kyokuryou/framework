package org.smarty.core.bean;

import org.quartz.Job;

/**
 * JobData
 */
public class JobProperty {
	private String groupName;
	private long startDelay;
	private long repeatInterval = 1;
	private int repeatCount = 1;

	private Class<? extends Job> jobClass;
	private String description;

	public JobProperty(String groupName, Class<? extends Job> jobClass) {
		this.groupName = groupName;
		this.jobClass = jobClass;
	}

	public Class<? extends Job> getJobClass() {
		return jobClass;
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

	public long getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
