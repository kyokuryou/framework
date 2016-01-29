package org.smarty.core.utils;

import org.junit.Test;
import org.smarty.core.test.AbsTestCase;

public class ConvertUtilTest extends AbsTestCase {

    @Test
    public void testReFileSuffix() {
        String sb = ConvertUtil.reFileSuffix("/home/abc.txt", "doc");
        System.out.println(sb);
    }
}
