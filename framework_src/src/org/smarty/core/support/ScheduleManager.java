package org.smarty.core.support;

import org.smarty.core.bean.ScheduleJob;
import org.smarty.core.logger.RuntimeLogger;
import org.smarty.core.utils.LogicUtil;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.InitializingBean;

import java.text.ParseException;

public class ScheduleManager implements InitializingBean {
    private static RuntimeLogger logger = new RuntimeLogger(ScheduleManager.class);
    // 调度器
    private Scheduler scheduler;

    public void afterPropertiesSet() throws Exception {

    }

    /**
     * Description: 启动一个自定义的job
     *
     * @param schedulingJob 自定义的job
     * @return 成功则返回true，否则返回false
     */
    public boolean enabled(ScheduleJob schedulingJob) {
        if (schedulingJob == null) {
            return false;
        }
        try {
            CronTrigger trigger = (CronTrigger) getJobTrigger(schedulingJob);
            if (trigger == null) {
                JobDetail jobDetail = getJobDetail(schedulingJob);
                trigger = getCronTrigger(schedulingJob);
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                trigger.setCronExpression(schedulingJob.getCronExpression());
                scheduler.rescheduleJob(trigger.getName(), trigger.getGroup(), trigger);
            }
        } catch (Exception e) {
            logger.out(e);
        }
        return true;
    }

    /**
     * 禁用一个job
     *
     * @param schedulingJob 自定义的job
     * @return 成功则返回true，否则返回false
     */
    public boolean disabled(ScheduleJob schedulingJob) {
        if (schedulingJob == null) {
            return false;
        }
        try {
            Trigger trigger = getJobTrigger(schedulingJob);
            if (trigger != null) {
                scheduler.deleteJob(schedulingJob.getJobId(), schedulingJob.getJobGroup());
            }
        } catch (SchedulerException e) {
            logger.out(e);
        }
        return true;
    }

    public CronTrigger getCronTrigger(ScheduleJob schedulingJob) throws ParseException {
        // 任务的名称
        String tn = schedulingJob.getTriggerName();
        // 任务所属组的名称
        String jg = schedulingJob.getJobGroup();
        // 定时任务运行时间表达式
        String ce = schedulingJob.getCronExpression();
        if (LogicUtil.isEmpty(tn) || LogicUtil.isEmpty(jg) || LogicUtil.isEmpty(ce)) {
            return null;
        }
        return new CronTrigger(tn, jg, ce);
    }

    /**
     * Description: 得到job的详细信息
     *
     * @return job的详细信息, 如果job不存在则返回null
     */
    public JobDetail getJobDetail(ScheduleJob schedulingJob) throws SchedulerException {
        // 任务ID
        String ji = schedulingJob.getJobId();
        // 任务所属组的名称
        String jg = schedulingJob.getJobGroup();
        // 执行类
        Class jc = schedulingJob.getJobClass();
        if (LogicUtil.isEmpty(ji) || LogicUtil.isEmpty(jg) || jc == null) {
            return null;
        }
        return new JobDetail(ji, jg, jc);
    }

    /**
     * Description: 得到job对应的Trigger
     *
     * @return job的Trigger, 如果Trigger不存在则返回null
     */
    public Trigger getJobTrigger(ScheduleJob schedulingJob) {
        // 任务的ID
        String ji = schedulingJob.getJobId();
        // 任务所属组的名称
        String jg = schedulingJob.getJobGroup();
        if (LogicUtil.isEmpty(ji) || LogicUtil.isEmpty(jg)) {
            return null;
        }
        try {
            return scheduler.getTrigger(ji + "Trigger", jg);
        } catch (SchedulerException e) {
            logger.out(e);
            return null;
        }
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
