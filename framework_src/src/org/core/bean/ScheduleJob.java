package org.core.bean;

import org.springframework.scheduling.quartz.QuartzJobBean;

public class ScheduleJob {
    // 任务启用状态
    public static final int JS_ENABLED = 0;
    // 任务禁用状态
    public static final int JS_DISABLED = 1;
    // 任务已删除状态
    public static final int JS_DELETE = 2;
    // 任务的Id，一般为所定义Bean的ID
    private String jobId;
    // 任务的名称
    private String jobName;
    // 任务所属组的名称
    private String jobGroup;
    // 任务的状态，0：启用；1：禁用；2：已删除
    private int jobStatus;
    // 定时任务运行时间表达式
    private String cronExpression;
    // 任务描述
    private String memos;
    // 异步的执行类，需要从MethodInvokingJob继承;同步的执行类，需要从StatefulMethodInvokingJob继承
    private Class<?> jobClass = QuartzJobBean.class;

    /**
     * 得到该job的Trigger名字
     *
     * @return
     */
    public String getTriggerName() {
        return this.getJobId() + "Trigger";
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public int getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(int jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getMemos() {
        return memos;
    }

    public void setMemos(String memos) {
        this.memos = memos;
    }

    public Class<?> getJobClass() {
        return jobClass;
    }

    public void setJobClass(Class<?> jobClass) {
        this.jobClass = jobClass;
    }
}