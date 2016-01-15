package org.smarty.core.common;

import java.util.TimeZone;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

/**
 * @author qul
 * @since LVGG1.1
 */
public abstract class ScheduleRunnable implements Runnable, FactoryBean<CronTrigger>, BeanNameAware, InitializingBean {
    private String triggerName;
    private String group;
    private String cronExpression;
    private String description;
    private TimeZone timeZone;

    private String name;
    private CronTrigger cronTrigger;

    public ScheduleRunnable(String triggerName, String cronExpression) {
        this.triggerName = triggerName;
        this.cronExpression = cronExpression;
    }

    public ScheduleRunnable(String triggerName, String group, String cronExpression) {
        this.triggerName = triggerName;
        this.cronExpression = cronExpression;
        this.group = group;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public final void afterPropertiesSet() throws Exception {
        CronTriggerFactoryBean ctfb = new CronTriggerFactoryBean();
        ctfb.setName(triggerName);
        ctfb.setGroup(group);
        ctfb.setCronExpression(cronExpression);
        ctfb.setDescription(description);
        ctfb.setJobDetail(createJobDetail());
        ctfb.setTimeZone(timeZone);
        ctfb.afterPropertiesSet();
        cronTrigger = ctfb.getObject();
    }

    private JobDetail createJobDetail() throws Exception {
        MethodInvokingJobDetailFactoryBean mijdfb = new MethodInvokingJobDetailFactoryBean();
        mijdfb.setName(name);
        mijdfb.setGroup(group);
        mijdfb.setTargetObject(this);
        mijdfb.setTargetMethod("run");
        mijdfb.afterPropertiesSet();
        return mijdfb.getObject();
    }

    @Override
    public final void setBeanName(String name) {
        this.name = name;
    }

    @Override
    public final CronTrigger getObject() throws Exception {
        return cronTrigger;
    }

    @Override
    public final boolean isSingleton() {
        return true;
    }

    @Override
    public final Class<?> getObjectType() {
        return CronTrigger.class;
    }
}
