package org.smarty.core.support.jdbc.support;

import org.smarty.core.bean.ClassElement;

/**
 *
 */
public class SessionClass {

    // 所在包
    public final static String MAPPER_PACKAGE = "mapper";
    // 后缀
    public final static String MAPPER_SUFFIX = "Mapper";

    private ClassElement classElement;

    public SessionClass(ClassElement classElement) {
        this.classElement = classElement;
    }

    public ClassElement getClassElement() {
        return classElement;
    }

    /**
     * 获得当前运行的类和方法
     *
     * @return classInfo
     */
    public static SessionClass getInstance(int level) {
        if (level < 0) {
            return null;
        }
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();

        if (stes == null || level > stes.length) {
            return null;
        }
        StackTraceElement ste = stes[level];

        ClassElement rci = new ClassElement();
        rci.setClassName(ste.getClassName());
        rci.setMethodName(ste.getMethodName());
        return new SessionClass(rci);
    }

    /**
     * 返回关联的XML文件
     *
     * @return xml文件路径
     */
    public String getXMLFile() {
        StringBuilder sb = new StringBuilder();
        String[] cn = classElement.getClassName().split("[.]");
        for (String c : cn) {
            if ("dao".equals(c)) {
                sb.append(MAPPER_PACKAGE).append("/");
            } else if ("impl".equals(c)) {
                sb.append("");
            } else if (c.endsWith("Dao")) {
                sb.append(c.substring(0, c.indexOf("Dao")));
                sb.append(MAPPER_SUFFIX);
                sb.append(".xml");
            } else {
                sb.append(c).append("/");
            }
        }
        return sb.toString();
    }
}
