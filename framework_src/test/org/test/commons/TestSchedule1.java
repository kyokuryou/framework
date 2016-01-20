package org.test.commons;

import java.util.Date;
import org.smarty.core.utils.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author qul
 * @since LVGG1.1
 */
@Component
public class TestSchedule1 {

    @Scheduled(cron = "0/1 * * * * ?")
    public void run() {
        long tm = System.currentTimeMillis();
        System.out.println("TestSchedule1=====" + DateUtil.format(new Date(tm)));
    }
}
