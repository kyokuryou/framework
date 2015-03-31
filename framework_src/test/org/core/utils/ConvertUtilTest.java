package org.core.utils;

import org.core.test.AbsTestCase;
import org.junit.Test;

public class ConvertUtilTest extends AbsTestCase {

    @Test
    public void testReFileSuffix(){
        String sb = ConvertUtil.reFileSuffix("/home/abc.txt","doc");
        System.out.println(sb);
    }
}
