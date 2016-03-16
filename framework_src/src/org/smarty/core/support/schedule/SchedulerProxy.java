package org.smarty.core.support.schedule;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.smarty.core.bean.JobProperty;
import org.smarty.core.utils.MD5Util;
import org.smarty.core.utils.ObjectUtil;
import org.springframework.scheduling.quartz.SchedulerAccessor;

/**
 * SchedulerProxy
 */
public final class SchedulerProxy extends SchedulerAccessor {
	private final Map<String, JobProperty> jobMap = new HashMap<String, JobProperty>();
	private final Scheduler scheduler;

	public SchedulerProxy(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void addJobProperty(JobProperty jobProperty) {
		ObjectUtil.assertNotEmpty(jobProperty, "jobProperty must not be null");
		long ri = jobProperty.getRepeatInterval();
		int rc = jobProperty.getRepeatCount();
		ObjectUtil.assertExpression(ri > 0, "repeat interval must gt zero");
		ObjectUtil.assertExpression(rc > 0 || rc == -1, "repeat count must gt zero or eq -1");
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

	@Override
	protected Scheduler getScheduler() {
		return scheduler;
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
		// 每次间隔
		trigger.setRepeatInterval(jobProperty.getRepeatInterval());
		// 循环次数
		trigger.setRepeatCount(jobProperty.getRepeatCount());
		trigger.setPriority(0);
		trigger.setMisfireInstruction(0);
		trigger.setDescription(description);
		trigger.setJobDataMap(jdm);
		return trigger;
	}

	private JobDetailImpl getJobDetail(JobKey jobKey, JobProperty jobProperty) {
		JobDetailImpl jdi = new JobDetailImpl();
		jdi.setKey(jobKey);
		jdi.setJobClass(jobProperty.getJobClass());
		jdi.setDurability(true);
		return jdi;
	}
}
