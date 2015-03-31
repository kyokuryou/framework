package org.core.build;

/**
 * Created with Liang Qu
 * Create User: Liang Qu
 * Update User: Liang Qu
 * Create Date: 2013/11/13
 * Update Date: 2013/11/13
 */
public enum WebVersion {
    V2_4, V2_5, V3_0;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        char[] chs = name().toCharArray();
        sb.append(chs[1]);
        sb.append(".");
        sb.append(chs[3]);
        return sb.toString();
    }
}
