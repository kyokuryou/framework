package org.smarty.core.support.schedule;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.smarty.core.bean.JobProperty;
import org.smarty.core.utils.MD5Util;
import org.smarty.core.utils.ObjectUtil;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * SchedulerProxy
 */
public final class SchedulerProxy extends SchedulerFactoryBean {
	private final Map<String, JobProperty> jobMap = new HashMap<String, JobProperty>();

	public void addJobProperty(List<JobProperty> jobs) {
		for (JobProperty job : jobs) {
			addJobProperty(job);
		}
	}

	public void addJobProperty(JobProperty jobProperty) {
		ObjectUtil.assertNotEmpty(jobProperty, "jobProperty must not be null");
		jobMap.put(jobProperty.getGroupName(), jobProperty);
	}

	public void registerJob(String groupName, JobDataMap jobDataMap) {
		ObjectUtil.assertNotEmpty(groupName, "groupName must not be null");
		JobProperty jp = getJobProperty(groupName);
		// 注册
		setTriggers(getTrigger(jobDataMap, jp));
		try {
			registerListeners();
			registerJobsAndTriggers();
		} catch (SchedulerException e) {
			logger.error(e);
		}
	}

	public void unregisterJob(String groupName, JobDataMap jobDataMap) {
		ObjectUtil.assertNotEmpty(groupName, "groupName must not be null");
		JobProperty jp = getJobProperty(groupName);
		JobDataMap jdm = getJobDataMap(jobDataMap);
		JobKey jk = getJobKey(jp, jdm);
		try {
			getScheduler().deleteJob(jk);
		} catch (SchedulerException e) {
			logger.error(e);
		}
	}

	private JobProperty getJobProperty(String groupName) {
		JobProperty jp = jobMap.get(groupName);
		ObjectUtil.assertNotEmpty(jp, groupName + " group is not definition");
		return jp;
	}

	private JobKey getJobKey(JobProperty jobProperty, JobDataMap jobDataMap) {
		String groupName = jobProperty.getGroupName();
		StringBuilder nb = new StringBuilder(groupName + "?");
		if (ObjectUtil.isEmpty(jobDataMap)) {
			nb.append("default");
			return createJobKey(nb.toString(), groupName);
		}
		String[] keys = jobDataMap.getKeys();
		Arrays.sort(keys);
		for (int i = 0, len = keys.length; i < len; i++) {
			String key = keys[i];
			nb.append(key).append("=");
			nb.append(jobDataMap.get(key));
			if (i < len - 1) {
				nb.append("&");
			}
		}
		return createJobKey(nb.toString(), groupName);
	}

	private JobKey createJobKey(String name, String text) {
		return JobKey.jobKey(MD5Util.encode(text), name);
	}

	private JobDataMap getJobDataMap(JobDataMap jobDataMap) {
		return jobDataMap != null ? jobDataMap : new JobDataMap();
	}

	private SimpleTriggerImpl getTrigger(JobDataMap jobDataMap, JobProperty jobProperty) {
		long startDelay = jobProperty.getStartDelay();
		String description = jobProperty.getDescription();
		JobDataMap jdm = getJobDataMap(jobDataMap);
		JobKey jobKey = getJobKey(jobProperty, jdm);
		// jobDetail
		jdm.put("jobDetail", getJobDetail(jobKey, jobProperty));
		// Trigger
		SimpleTriggerImpl trigger = new SimpleTriggerImpl();
		long st = System.currentTimeMillis();
		if (startDelay > 0) {
			st += startDelay;
		}
		trigger.setName(jobKey.getName());
		trigger.setGroup(jobKey.getGroup());
		trigger.setJobKey(jobKey);
		trigger.setStartTime(new Date(st));
		trigger.setRepeatInterval(1);
		trigger.setRepeatCount(0);
		trigger.setPriority(0);
		trigger.setMisfireInstruction(0);
		trigger.setDescription(description);
		trigger.setJobDataMap(jdm);
		return trigger;
	}

	private JobDetailImpl getJobDetail(JobKey jobKey, JobProperty jobProperty) {
		JobDetailImpl jdi = new JobDetailImpl();
		jdi.setKey(jobKey);
		jdi.setJobClass(jobProperty.getJobRunnable());
		jdi.setDurability(true);
		return jdi;
	}
}
