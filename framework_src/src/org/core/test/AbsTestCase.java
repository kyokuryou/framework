package org.core.test;

import org.core.utils.SpringUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.sql.DataSource;
import java.io.PrintStream;

/**
 * junit自定义扩展接口定义.所有要实现junit的类必须继承.
 */
public abstract class AbsTestCase {
    protected static PrintStream out = System.out;
    protected static DataSource dataSource;

    protected void setUpSpring(String... files) {
        StringBuilder sb = new StringBuilder();
        sb.append("================================\n");
        sb.append("==获得SpringContext-spring.xml==\n");
        sb.append("================================\n");
        out.println(sb.toString());
        SpringUtil.initApplicationContext(files);
        // dataSource = SpringUtil.getBean("dataSource", DataSource.class);
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("================================\n");
        sb.append("======  启动Junit测试环境  ======\n");
        sb.append("================================\n");
        out.println(sb.toString());
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("================================\n");
        sb.append("======  停止Junit测试环境  ======\n");
        sb.append("================================\n");
        out.println(sb.toString());
    }
}
