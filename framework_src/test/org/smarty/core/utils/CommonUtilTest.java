package org.smarty.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import org.junit.Test;
import org.smarty.core.test.AbsTestCase;

/**
 *
 */
public class CommonUtilTest extends AbsTestCase {

    public void testGetGroupCount() {
        byte[] bytes = new byte[49];

        for (int i = 0; i < 49; i++) {
            bytes[i] = (byte) i;
        }

        int gc = CommonUtil.getGroupCount(bytes, 10);
        for (int i = 0; i < gc; i++) {
            System.out.println("=====第" + i + "开始=====");
            if (i == gc - 1) {
                for (int j = i * 10; j < bytes.length; j++) {

                    System.out.println(bytes[j]);
                }
            } else {
                for (int j = i * 10; j < i * 10 + 10; j++) {

                    System.out.println(bytes[j]);
                }
            }
            System.out.println("=====第" + i + "结束=====");

        }
        System.out.println(CommonUtil.getGroupCount(bytes, 10));
    }

    public void testToDBField() {
        System.out.println(CommonUtil.toDBField("name"));
        System.out.println(CommonUtil.toDBField("nameA"));
        System.out.println(CommonUtil.toDBField("NameB"));
        System.out.println(CommonUtil.toDBField("NameZ"));
        System.out.println(CommonUtil.toDBField("Namea"));
        System.out.println(CommonUtil.toDBField("Nameb"));
        System.out.println(CommonUtil.toDBField("Namez"));
        System.out.println(CommonUtil.toDBField("Name@"));
        System.out.println(CommonUtil.toDBField("Name]"));
        System.out.println(CommonUtil.toDBField("Name1"));
        System.out.println(CommonUtil.toDBField("Name2"));
    }

    @Test
    public void testGetUUID() throws Exception {
        File file = new File("D:/home/Desktop/uuid.txt");
        FileOutputStream fos = new FileOutputStream(file);
        for (int i = 0; i < 4000; i++) {
            String uid = CommonUtil.getUUID();
            fos.write(uid.getBytes());
            fos.write('\r');
            fos.write('\n');
            fos.flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        fos.close();
    }
}
