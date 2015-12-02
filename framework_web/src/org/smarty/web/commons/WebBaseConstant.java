package org.smarty.web.commons;

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
    String CAPTCHA_RANDOM_WORD = "ABCDEFGHIJKLMNPQRSTUVWXYZ";
    // 验证码输入表单名称
    String CAPTCHA_INPUT_NAME = "j_captcha";
    // 验证码图片URL
    String CAPTCHA_IMAGE_URL = "/captcha.jpg";

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

}
