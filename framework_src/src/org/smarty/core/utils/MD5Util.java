package org.smarty.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * MD5加密工具类
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class MD5Util {
	private static Log logger = LogFactory.getLog(MD5Util.class);
	private final static String[] strDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

	// 返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	// 返回形式只为数字
	/*private static String byteToNum(byte bByte) {
        int iRet = bByte;
		System.out.println("iRet1=" + iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		return String.valueOf(iRet);
	}*/

	// 转换字节数组为16进制字串
	private static String byteToString(byte[] bByte) {
		StringBuilder sb = new StringBuilder();
		for (byte aBByte : bByte) {
			sb.append(byteToArrayString(aBByte));
		}
		return sb.toString();
	}

	public static String encode(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return byteToString(md.digest(str.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			logger.warn(e);
		}
		return null;
	}
}
