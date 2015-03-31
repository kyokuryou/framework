package org.core;

import org.core.bean.Cache;
import org.core.support.cache.CacheMessage;
import org.core.test.UserBean;
import org.core.utils.SpringUtil;
import org.core.utils.TemplateUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 */
public class TestMain {
    private static final Integer IMAGE_HEIGHT = 28;// 验证码图片高度
    private static final Integer IMAGE_WIDTH = 80;// 验证码图片宽度

    public static void main(String[] args) throws Exception {
        TestMain tm = new TestMain();
        OutputStream os = new FileOutputStream("d:/test.jpg");
        BufferedImage bi = tm.getBackground();
        ImageIO.write(bi, "JPEG", os);
        os.flush();
        os.close();
    }

    public BufferedImage getBackground() {
        Random random = new Random();
        BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(IMAGE_WIDTH);
            int y = random.nextInt(IMAGE_HEIGHT);
            int xl = random.nextInt(IMAGE_WIDTH / 2);
            int yl = random.nextInt(IMAGE_HEIGHT / 2);
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


    public static void main4(String[] args) {
        initCache();
        Map<String, String> map = new HashMap<String, String>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");
        CacheMessage.putSystemCache("testCache", map);
//        new TemplateUtil("testCache");

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("a", "a1");


        String s = TemplateUtil.render("select * from table where a={$testCache.a},b={$testCache.b},c={$testCache.c},a1={$a}", map1);
        System.out.println(s);
    }

    /**
     * 初始化缓存容器
     */
    public static void initCache() {
        Map<String, Integer> caches = new HashMap<String, Integer>();
        caches.put("system", 512);
        caches.put("temporary", 512);
        CacheMessage cm = new CacheMessage("q1w2e3r4t5");
        cm.initCacheMap(caches);
    }

    public static void main3(String[] args) {
        char[] c = {'C', 'a', 'c', 'h', 'e'};
        int c2 = 0;
        for (char c1 : c) {
            c2 += c1;
        }
        System.out.println(c2);
    }

    public static void main2(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("a", "1");
        Cache c = new Cache(map, false);
        Cache c1 = c.cloneCache();
        Map<String, String> m = (Map<String, String>) c1.getData();
        m.put("a", "2");
        System.out.println(c.getData());
        System.out.println(c1.getData());
    }

    public static void main1(String[] args) {
        SpringUtil.initApplicationContext("classpath:org/test/spring-userBean.xml");
        UserBean tb = SpringUtil.getBean("userBean1", UserBean.class);
        System.out.println(tb);
    }
}
