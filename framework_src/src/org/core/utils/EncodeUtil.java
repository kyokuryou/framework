package org.core.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.core.logger.RuntimeLogger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 各种格式的编码加码工具类.
 *
 * @author taiqichao
 */
public class EncodeUtil {
    private static RuntimeLogger logger = new RuntimeLogger(EncodeUtil.class);
    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    private EncodeUtil() {

    }

    /**
     * Hex编码.
     */
    public static String hexEncode(byte[] input) {
        return new String(Hex.encodeHex(input));
    }

    /**
     * Hex解码.
     */
    public static byte[] hexDecode(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            logger.out("Hex Decoder exception:", e);
        }
        return new byte[0];
    }

    /**
     * Base64编码.
     */
    public static String base64Encode(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    /**
     * Base64解码.
     */
    public static byte[] base64Decode(String input) {
        return Base64.decodeBase64(input.getBytes());
    }

    /**
     * URL 编码, Encode默认为UTF-8.
     */
    public static String urlEncode(String input) {
        return urlEncode(input, DEFAULT_URL_ENCODING);
    }

    /**
     * URL 编码.
     */
    public static String urlEncode(String input, String encoding) {
        try {
            return URLEncoder.encode(input, encoding);
        } catch (UnsupportedEncodingException e) {
            logger.out(e);
        }
        return "";
    }
}
