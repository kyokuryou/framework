package org.smarty.core;

import org.junit.Test;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;
import org.smarty.core.support.schedule.DispatcherSchedule;
import org.smarty.core.test.AbsTestCase;
import org.smarty.core.utils.SpringUtil;

/**
 * Created by kyokuryou on 15-4-1.
 */
public class TestFramework extends AbsTestCase {
    @Test
    public void testFramework() throws SchedulerException {
        setUpSpring("classpath:/spring.xml");
        DispatcherSchedule ds = SpringUtil.getBean("testSchedule1", DispatcherSchedule.class);

        JobDataMap jdm = new JobDataMap();
        jdm.put("orderId", "abc");

        ds.startJob(jdm);
        JobDataMap jdm1 = new JobDataMap();
        jdm1.put("orderId", "++----sdfsdf");
        ds.startJob(jdm1);

        try {
            Thread.sleep(1000 * 60 * 60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
