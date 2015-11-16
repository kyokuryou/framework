package org.smarty.core;

import org.junit.Test;
import org.smarty.core.test.AbsTestCase;
import org.smarty.core.utils.SpringUtil;
import org.test.service.TestService;

/**
 * Created by kyokuryou on 15-4-1.
 */
public class TestFramework extends AbsTestCase {
    @Test
    public void testFramework() {
        setUpSpring("classpath:/spring.xml");
        TestService ts = SpringUtil.getBean("testService", TestService.class);
        System.out.println(ts.insert1() + "==================");
    }

}
