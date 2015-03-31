package org.core.utils;

import org.core.test.AbsTestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class TemplateUtilTest extends AbsTestCase {

    @Test
    public void testRender() {
        Map a = new HashMap();
        a.put("a-1","1");

        List l = new ArrayList();
        l.add(a);

        Map amp = new HashMap();
        amp.put("abc", null);
//        System.out.println(TemplateUtil.render("SELECT * FROM TABLE WHERE col in (#{ins value=$abc item='a-1'})", amp));
        System.out.println(TemplateUtil.render("SELECT  COUNT(1)  FROM  fk_area  WHERE  name = :name  AND  is_delete = 0  #{if $abc!=null}  AND  pk_id = :pkId  #{/if}", amp));
    }
}
