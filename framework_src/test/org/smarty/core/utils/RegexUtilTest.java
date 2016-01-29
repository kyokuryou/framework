package org.smarty.core.utils;

import org.junit.Test;
import org.smarty.core.test.AbsTestCase;

/**
 *
 */
public class RegexUtilTest extends AbsTestCase {
    @Test
    public void testConvertSQL() throws Exception {
        String sql = "select * from table                                where col=:and \t\n\r and acb='and'";
        System.out.println("---" + RegexUtil.convertSQL(sql) + "----------");

    }

    @Test
    public void testIsFirstPunct() {
        String abc = ":abc";
        System.out.println(RegexUtil.isFirstPunct(abc));
    }
}
