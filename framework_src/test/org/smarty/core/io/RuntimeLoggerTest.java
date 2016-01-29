package org.smarty.core.io;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author qul
 * @since LVGG1.1
 */
public class RuntimeLoggerTest {
    public static void main(String[] args) {
        Log logger = LogFactory.getLog(RuntimeLoggerTest.class);
        System.out.println(logger.isDebugEnabled());
    }
}
