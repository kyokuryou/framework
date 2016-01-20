package org.smarty.core.io;

/**
 * @author qul
 * @since LVGG1.1
 */
public class RuntimeLoggerTest {
    public static void main(String[] args) {
        RuntimeLogger logger = new RuntimeLogger(RuntimeLoggerTest.class);
        System.out.println(logger.isDebugEnabled());
    }
}
