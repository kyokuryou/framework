package org.smarty.web.commons;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 生成验证码图片
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class CaptchaEngine extends ListImageCaptchaEngine {

    public static final String IMAGE_CAPTCHA_KEY = "imageCaptcha";// ImageCaptcha对象存放在Session中的key
    public static final String CAPTCHA_INPUT_NAME = "j_captcha";// 验证码输入表单名称
    public static final String CAPTCHA_IMAGE_URL = "/captcha.jpg";// 验证码图片URL

    // 验证码随机字体
    private static final Font[] RANDOM_FONT = new Font[]{
            new Font("nyala", Font.BOLD, WebBaseConstant.CAPTCHA_MIN_FONT),
            new Font("Arial", Font.BOLD, WebBaseConstant.CAPTCHA_MIN_FONT),
            new Font("Bell MT", Font.BOLD, WebBaseConstant.CAPTCHA_MIN_FONT),
            new Font("Credit valley", Font.BOLD, WebBaseConstant.CAPTCHA_MIN_FONT),
            new Font("Impact", Font.BOLD, WebBaseConstant.CAPTCHA_MIN_FONT)
    };

    // 验证码随机颜色
    private static final Color[] RANDOM_COLOR = new Color[]{
            new Color(255, 255, 255),
            new Color(255, 220, 220),
            new Color(220, 255, 255),
            new Color(220, 220, 255),
            new Color(255, 255, 220),
            new Color(220, 255, 220)
    };

    // 生成验证码
    protected void buildInitialFactories() {
        RandomListColorGenerator randomListColorGenerator = new RandomListColorGenerator(RANDOM_COLOR);

        BackgroundGenerator backgroundGenerator = new RandomBackgroundGenerator();

        WordGenerator wordGenerator = new RandomWordGenerator(WebBaseConstant.CAPTCHA_RANDOM_WORD);

        FontGenerator fontGenerator = new RandomFontGenerator(WebBaseConstant.CAPTCHA_MIN_FONT, WebBaseConstant.CAPTCHA_MAX_FONT, RANDOM_FONT);

        TextDecorator[] textDecorator = new TextDecorator[]{};

        TextPaster textPaster = new DecoratedRandomTextPaster(WebBaseConstant.CAPTCHA_MIN_WORD, WebBaseConstant.CAPTCHA_MAX_WORD, randomListColorGenerator, textDecorator);

        WordToImage wordToImage = new ComposedWordToImage(fontGenerator, backgroundGenerator, textPaster);

        addFactory(new GimpyFactory(wordGenerator, wordToImage));
    }

    private class RandomBackgroundGenerator implements BackgroundGenerator {

        public int getImageHeight() {
            return WebBaseConstant.CAPTCHA_IMAGE_WIDTH;
        }

        public int getImageWidth() {
            return WebBaseConstant.CAPTCHA_IMAGE_HEIGHT;
        }

        public BufferedImage getBackground() {
            Random random = new Random();
            BufferedImage image = new BufferedImage(WebBaseConstant.CAPTCHA_IMAGE_WIDTH, WebBaseConstant.CAPTCHA_IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            // 设定背景色
            g.setColor(getRandColor(200, 250));
            g.fillRect(0, 0, WebBaseConstant.CAPTCHA_IMAGE_WIDTH, WebBaseConstant.CAPTCHA_IMAGE_HEIGHT);
            g.setColor(getRandColor(160, 200));
            for (int i = 0; i < 10; i++) {
                int x = random.nextInt(WebBaseConstant.CAPTCHA_IMAGE_WIDTH);
                int y = random.nextInt(WebBaseConstant.CAPTCHA_IMAGE_HEIGHT);
                int xl = random.nextInt(WebBaseConstant.CAPTCHA_IMAGE_WIDTH / 2);
                int yl = random.nextInt(WebBaseConstant.CAPTCHA_IMAGE_HEIGHT / 2);
                g.drawLine(x, y, x + xl, y + yl);
            }
            g.dispose();
            return image;
        }

        /**
         * 给定范围获得随机颜色
         *
         * @param fc
         * @param bc
         * @return
         */
        public Color getRandColor(int fc, int bc) {
            Random random = new Random();
            if (fc > 255) fc = 255;
            if (bc > 255) bc = 255;
            int r = fc + random.nextInt(bc - fc);
            int g = fc + random.nextInt(bc - fc);
            int b = fc + random.nextInt(bc - fc);
            return new Color(r, g, b);
        }
    }
}