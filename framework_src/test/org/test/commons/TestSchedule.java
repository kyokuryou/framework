package org.test.commons;

import java.util.Date;
import org.quartz.JobDataMap;
import org.smarty.core.support.schedule.JobRunnable;
import org.smarty.core.utils.DateUtil;

/**
 * @author qul
 * @since LVGG1.1
 */
public class TestSchedule extends JobRunnable {

    @Override
    public void run(JobDataMap dataMap) {
        long tm = System.currentTimeMillis();
        System.out.println("TestSchedule" + dataMap.get("orderId") + "-------------------------" + DateUtil.format(new Date(tm)));
    }
}
