package org.smarty.web.commons;

import java.awt.Color;
import java.awt.Font;
import org.smarty.core.common.BaseConstant;

/**
 * @author qul
 * @since LVGG1.1
 */
public interface WebBaseConstant extends BaseConstant {
	int CAPTCHA_MAX_WORD = 4;
	int CAPTCHA_MIN_WORD = 4;
	int CAPTCHA_IMAGE_HEIGHT = 28;
	// 验证码图片宽度
	int CAPTCHA_IMAGE_WIDTH = 80;
	// 验证码最小字体
	int CAPTCHA_MIN_FONT = 16;
	// 验证码最大字体
	int CAPTCHA_MAX_FONT = 16;
	// 随机字符
	String CAPTCHA_RANDOM_WORD = "ABCDEFGHJKLMNPQRSTUVWXY23456789";
	// 验证码随机字体
	Font[] CAPTCHA_TEXT_FONT = new Font[]{
			new Font("nyala", Font.BOLD, WebBaseConstant.CAPTCHA_MIN_FONT),
			new Font("Arial", Font.BOLD, WebBaseConstant.CAPTCHA_MIN_FONT),
			new Font("Bell MT", Font.BOLD, WebBaseConstant.CAPTCHA_MIN_FONT),
			new Font("Credit valley", Font.BOLD, WebBaseConstant.CAPTCHA_MIN_FONT),
			new Font("Impact", Font.BOLD, WebBaseConstant.CAPTCHA_MIN_FONT)
	};

	// 验证码随机颜色
	Color[] CAPTCHA_TEXT_COLOR = new Color[]{
			new Color(128, 128, 128).darker(),
			new Color(64, 64, 64),
			new Color(0, 0, 0).brighter(),
			new Color(255, 0, 0).brighter(),
			new Color(255, 0, 255).brighter(),
			new Color(0, 0, 255).brighter()

	};

	// 背景色
	Color[] CAPTCHA_BACKGROUND_COLOR = new Color[]{
			new Color(255, 175, 175),
			new Color(255, 200, 0).brighter(),
			new Color(255, 255, 0),
			new Color(0, 255, 0).brighter(),
			new Color(0, 255, 255).brighter()
	};

	String VIEW_VIEW = "view";
	String VIEW_LIST = "list";
	String VIEW_INPUT = "input";
	String VIEW_STATUS = "status";
	String VIEW_WARN = "warn";
	String VIEW_SUCCESS = "success";
	String VIEW_ERROR = "error";
	String VIEW_MESSAGE = "message";

	int TIME_OUT = 10000;
	long REQ_MAX_DATA = 10 * 1024 * 1024L;

	// 连接超时
	int CONNECT_TIMEOUT = 5000;
	// 读取超时
	int READ_TIMEOUT = 10000;

}
