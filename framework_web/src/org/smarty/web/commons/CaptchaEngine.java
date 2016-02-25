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
import java.awt.Color;
import java.awt.Graphics;
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

	// 生成验证码
	protected void buildInitialFactories() {
		RandomListColorGenerator rlcg = new RandomListColorGenerator(
				WebBaseConstant.CAPTCHA_TEXT_COLOR
		);

		BackgroundGenerator bg = new RandomBackgroundGenerator();

		WordGenerator wg = new RandomWordGenerator(WebBaseConstant.CAPTCHA_RANDOM_WORD);

		FontGenerator fg = new RandomFontGenerator(
				WebBaseConstant.CAPTCHA_MIN_FONT,
				WebBaseConstant.CAPTCHA_MAX_FONT,
				WebBaseConstant.CAPTCHA_TEXT_FONT
		);

		TextPaster textPaster = new DecoratedRandomTextPaster(
				WebBaseConstant.CAPTCHA_MIN_WORD,
				WebBaseConstant.CAPTCHA_MAX_WORD,
				rlcg,
				new TextDecorator[]{}
		);

		WordToImage wordToImage = new ComposedWordToImage(fg, bg, textPaster);

		addFactory(new GimpyFactory(wg, wordToImage));
	}

	private class RandomBackgroundGenerator implements BackgroundGenerator {

		public int getImageHeight() {
			return WebBaseConstant.CAPTCHA_IMAGE_HEIGHT;
		}

		public int getImageWidth() {
			return WebBaseConstant.CAPTCHA_IMAGE_WIDTH;
		}

		public BufferedImage getBackground() {
			int width = getImageWidth();
			int height = getImageHeight();
			Random random = new Random();
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			// 设定背景色
			g.setColor(getRandColor());
			g.fillRect(0, 0, width, height);
			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 10; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(width / 2);
				int yl = random.nextInt(height / 2);
				g.drawLine(x, y, x + xl, y + yl);
			}
			g.dispose();
			return image;
		}

		/**
		 * 给定范围获得随机颜色
		 *
		 * @return
		 */
		public Color getRandColor() {
			int len = WebBaseConstant.CAPTCHA_BACKGROUND_COLOR.length;
			Random random = new Random();
			int index = random.nextInt(len);
			return WebBaseConstant.CAPTCHA_BACKGROUND_COLOR[index];
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
			if (fc > 255)
				fc = 255;
			if (bc > 255)
				bc = 255;
			int r = fc + random.nextInt(bc - fc);
			int g = fc + random.nextInt(bc - fc);
			int b = fc + random.nextInt(bc - fc);
			return new Color(r, g, b);
		}
	}
}