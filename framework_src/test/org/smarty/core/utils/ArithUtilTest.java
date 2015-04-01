package org.smarty.core.utils;

import junit.framework.Assert;
import org.junit.Test;
import org.smarty.core.test.AbsTestCase;

/**
 * Created by kyokuryou on 15-4-1.
 */
public class ArithUtilTest extends AbsTestCase {
    @Test
    public void testAdd() {
        setUpSpring("classpath:/spring.xml");
        double num1 = 2.2223, num2 = 1.1112;
        float num3= 3.44f;
        Assert.assertEquals(ArithUtil.add(num1, num2), num1 + num2);
        Assert.assertEquals(ArithUtil.sub(num1, num2), 1.1111);
        Assert.assertEquals(ArithUtil.mul(num1, num2), num1 * num2);
        Assert.assertEquals(ArithUtil.div(num1, num2), 1.9999100072);

        Assert.assertEquals(ArithUtil.div(num1, num2, 2), 2.00);
        Assert.assertEquals(ArithUtil.round(num1, 2), 2.22);

        Assert.assertEquals(ArithUtil.convertsToFloat(num1), 2.2223f);
        Assert.assertEquals(ArithUtil.convertsToInt(num1),2);
        Assert.assertEquals(ArithUtil.convertsToLong(num1),2);

        Assert.assertEquals(ArithUtil.returnMax(num1, num2), num1);
        Assert.assertEquals(ArithUtil.returnMin(num1, num2), num2);
        Assert.assertEquals(ArithUtil.compareTo(num1, num2), 1);
        Assert.assertEquals(ArithUtil.getDecimals(num1), 4);
        Assert.assertEquals(ArithUtil.getDecimals(num3), 2);
        Assert.assertEquals(ArithUtil.getCurrencyFormat(), "#0.00");
    }

}
