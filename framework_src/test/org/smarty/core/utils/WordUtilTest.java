package org.smarty.core.utils;

import org.smarty.core.test.AbsTestCase;
import org.smarty.core.utils.WordUtil.ReadWord;
import org.junit.Test;

import java.io.IOException;

/**
 *
 */
public class WordUtilTest extends AbsTestCase {

    /**
     *             /^\s*((.|\n)*\S)?\s*$/
     */
    @Test
    public void testReadWord() {
        try {
            ReadWord rw = WordUtil.instanceRead("E:/test.doc");


//            Word w = rw.getTextAll();
//            for(String con : w.getComments()){
//                System.out.println(con);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
