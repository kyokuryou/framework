package org.smarty.core.support.jdbc;

import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;
import org.smarty.core.entity.TestModel;
import org.smarty.core.support.jdbc.sql.SQL;
import org.smarty.core.test.AbsTestCase;
import org.smarty.core.utils.CommonUtil;
import org.smarty.core.utils.SpringUtil;

/**
 * @author qul
 * @since LVGG1.1
 */
public class TestSQLSession extends AbsTestCase {
    @Test
    public void testQueryForModel() throws Exception {
        setUpSpring("classpath:spring.xml");

        //SQLSession testSQLSession = SpringUtil.getBean("testSqlSession", SQLSession.class);
//        System.out.println(testSQLSession);
//
//        if (testSQLSession == null) {
//            return;
//        }

        SelectSQL.BEGIN();
        SelectSQL.SELECT("COUNT(1)");
        SelectSQL.FROM("t_comment as c");
        SelectSQL.WHERE("c.deleted=1");
        SelectSQL.AND();
        SelectSQL.WHERE("c.type='note'");
        SelectSQL.AND();
        SelectSQL.WHERE("c.tid=n.id");
        SQL sql = SelectSQL.SQL();


        SelectSQL.BEGIN();
        SelectSQL.SELECT("n.id,n.gid");
        SelectSQL.SELECT("n.`name`");
        SelectSQL.SELECT("(" + sql.toString() + ") AS msg_cnt");
        SelectSQL.SELECT("u.id AS `user.id`");
        SelectSQL.SELECT("u.`name` AS `user.name`");
        SelectSQL.FROM("t_note AS n");
        SelectSQL.LEFT_OUTER_JOIN("t_user AS u ON n.uid=u.id");
        SelectSQL.WHERE("n.area_id=7");
        SelectSQL.LIMIT(0,1);

//        List<TestModel> tms = testSQLSession.queryForModelList(SelectSQL.SQL(), TestModel.class);

        System.out.println(SelectSQL.SQL().toString());
    }

    @Test
    public void testName() throws Exception {
        String key = "name_key.name_key";
        System.out.println(CommonUtil.toJavaField(key));
    }
}
