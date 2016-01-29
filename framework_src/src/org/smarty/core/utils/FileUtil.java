package org.smarty.core.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author qul
 * @since LVGG1.1
 */
public class FileUtil {
	private static Log logger = LogFactory.getLog(FileUtil.class);

	/**
	 * byte数组转换成16进制字符串
	 *
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return "";
		}
		for (byte s : src) {
			int v = s & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static String getSha1(byte[] data) throws IOException {
		return getDigest(data, "SHA-1");
	}


	public static String getMd5(byte[] data) throws IOException {
		return getDigest(data, "MD5");
	}

	public static String getDigest(byte[] data, String algorithm) throws IOException {
		if (data == null || data.length == 0) {
			return null;
		}
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(data);
			String format32 = new BigInteger(1, md.digest()).toString(32);
			return format32.toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
