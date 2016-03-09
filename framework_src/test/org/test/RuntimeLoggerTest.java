package org.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.quartz.SchedulerAccessorBean;

/**
 * @author qul
 * @since LVGG1.1
 */
public class RuntimeLoggerTest {
    public static void main(String[] args) {
        SchedulerAccessorBean sab = new SchedulerAccessorBean();
        sab.setGlobalTriggerListeners();


        Log logger = LogFactory.getLog(RuntimeLoggerTest.class);
        System.out.println(logger.isDebugEnabled());
    }
}
