package org.test;

/**
 * Created Date 2015/04/15
 *
 * @author kyokuryou
 * @version 1.0
 */
public class TestMain {
    public static void main(String[] args) {
        String name = TestMain.class.getSimpleName();
        char[] nms = name.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char nm : nms) {
            if (sb.length() > 0 && Character.isUpperCase(nm)) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(nm));
        }
        sb.delete(sb.lastIndexOf("_"),sb.length());
        System.out.println(sb.toString());

    }
}
