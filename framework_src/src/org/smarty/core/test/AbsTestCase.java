package org.smarty.core.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.smarty.core.common.BaseConstant;
import org.smarty.core.utils.SpringUtil;

import javax.sql.DataSource;

/**
 * junit自定义扩展接口定义.所有要实现junit的类必须继承.
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public abstract class AbsTestCase {
    protected static DataSource dataSource;

    protected void setUpSpring(String... files) {
        StringBuilder sb = new StringBuilder();
        sb.append("================================\n");
        sb.append("==获得SpringContext-spring.xml==\n");
        sb.append("================================\n");
        BaseConstant.DEF_OUT.println(sb.toString());
        SpringUtil.initApplicationContext(files);
        // dataSource = SpringUtil.getBean("dataSource", DataSource.class);
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("================================\n");
        sb.append("======  启动Junit测试环境  ======\n");
        sb.append("================================\n");
        BaseConstant.DEF_OUT.println(sb.toString());
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("================================\n");
        sb.append("======  停止Junit测试环境  ======\n");
        sb.append("================================\n");
        BaseConstant.DEF_OUT.println(sb.toString());
    }
}
