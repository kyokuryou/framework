package org.smarty.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.common.BaseConstant;

/**
 * 各种格式的编码加码工具类.
 * <p/>
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class EncodeUtil {
    private static Log logger = LogFactory.getLog(EncodeUtil.class);

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
            logger.warn("Hex Decoder exception:", e);
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
        return urlEncode(input, BaseConstant.DEF_ENCODE);
    }

    /**
     * URL 编码.
     */
    public static String urlEncode(String input, Charset charset) {
        try {
            return URLEncoder.encode(input, charset.name());
        } catch (UnsupportedEncodingException e) {
            logger.warn(e);
        }
        return "";
    }
}
