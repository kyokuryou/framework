package org.smarty.core.utils;

import com.mortennobel.imagescaling.ResampleOp;
import org.smarty.core.logger.RuntimeLogger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 图片处理工具类(该类对图片的处理基于imagescaling第三方组件)
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class ImageUtil {
    private static RuntimeLogger logger = new RuntimeLogger(ImageUtil.class);

    private ImageUtil() {
    }

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

    /**
     * 根据文件流读取图片文件真实类型
     *
     * @param in
     * @return
     */
    public static String getRealType(InputStream in) throws IOException {
        if (in == null) {
            return null;
        }
        byte[] b = new byte[4];
        // 重置流
        in.reset();
        // 读取前4个字节
        if (in.read(b) == -1) {
            return null;
        }
        String type = bytesToHexString(b).toUpperCase();
        if (type.contains("FFD8FF")) {
            return "jpg";
        } else if (type.contains("89504E47")) {
            return "png";
        } else if (type.contains("47494638")) {
            return "gif";
        } else if (type.contains("49492A00")) {
            return "tif";
        } else if (type.contains("424D")) {
            return "bmp";
        }
        return type;
    }

    public static String getSha1(InputStream in) throws IOException {
        return getDigest(in, "SHA-1");
    }


    public static String getMd5(InputStream in) throws IOException {
        return getDigest(in, "MD5");
    }

    public static String getDigest(InputStream in, String algorithm) throws IOException {
        if (in == null) {
            return null;
        }
        byte[] b = new byte[1024];
        int len = 0;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            // 重置流
            in.reset();
            // 读取
            while ((len = in.read(b)) != -1) {
                md.update(b, 0, len);
            }
            return new BigInteger(1, md.digest()).toString(32);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 缩放图片jpg
     *
     * @param in         原图片
     * @param targetFile 缩放后图片
     * @param width      指定宽度
     * @param height     指定高度
     * @param db         是否等比例缩放
     * @throws java.io.IOException
     */
    public static void resizeJPG(InputStream in, File targetFile, int width, int height, boolean db) throws IOException {
        if (in == null) {
            return;
        }
        try {
            // 重置流
            in.reset();
            BufferedImage image = ImageIO.read(in);
            double srcWidth = image.getWidth();
            double srcHeigth = image.getHeight();
            int zoomWidth = width;
            int zoomHeight = height;
            if (db) {
                // 等比例缩放
                double tempHeight = (srcHeigth / srcWidth) * width;
                if (tempHeight > height) {
                    zoomWidth = (int) ((srcWidth / srcHeigth) * height);
                    zoomHeight = height;
                } else {
                    zoomWidth = width;
                    zoomHeight = (int) ((srcHeigth / srcWidth) * width);
                }
            }
            ResampleOp resampleOp = new ResampleOp(zoomWidth, zoomHeight);
            BufferedImage tag = resampleOp.filter(image, null);
            ImageIO.write(tag, "JPEG", targetFile);
        } catch (IOException e) {
            logger.out("ImageUtils resize this image IOException", e);
        }
    }

    /**
     * 缩放图片jpg
     *
     * @param in   原图片
     * @param file 缩放后图片
     * @throws java.io.IOException
     */
    public static void originalJPG(InputStream in, File file) throws IOException {
        if (in == null) {
            return;
        }
        try {
            // 重置流
            in.reset();
            BufferedImage image = ImageIO.read(in);
            ImageIO.write(image, "JPEG", file);
        } catch (IOException e) {
            logger.out("ImageUtils original this image IOException", e);
        }
    }

    /**
     * 缩放图片png
     *
     * @param in         原图片
     * @param targetFile 缩放后图片
     * @param width      指定宽度
     * @param height     指定高度
     * @param db         是否等比例缩放
     * @throws java.io.IOException
     */
    public static void resizePNG(InputStream in, File targetFile, int width, int height, boolean db) throws IOException {
        if (in == null) {
            return;
        }
        try {
            // 重置流
            in.reset();
            BufferedImage image = ImageIO.read(in);
            double srcWidth = image.getWidth();
            double srcHeigth = image.getHeight();
            int zoomWidth = width;
            int zoomHeight = height;
            if (db) {
                // 等比例缩放
                double tempHeight = (srcHeigth / srcWidth) * width;
                if (tempHeight > height) {
                    zoomWidth = (int) ((srcWidth / srcHeigth) * height);
                    zoomHeight = height;
                } else {
                    zoomWidth = width;
                    zoomHeight = (int) ((srcHeigth / srcWidth) * width);
                }
            }
            ResampleOp resampleOp = new ResampleOp(zoomWidth, zoomHeight);
            BufferedImage tag = resampleOp.filter(image, null);
            ImageIO.write(tag, "PNG", targetFile);
        } catch (IOException e) {
            logger.out("ImageUtils resize this image IOException", e);
        }
    }

    /**
     * 缩放图片png
     *
     * @param in   原图片
     * @param file 缩放后图片
     * @throws java.io.IOException
     */
    public static void originalPNG(InputStream in, File file) throws IOException {
        if (in == null) {
            return;
        }
        try {
            // 重置流
            in.reset();
            BufferedImage image = ImageIO.read(in);
            ImageIO.write(image, "PNG", file);
        } catch (IOException e) {
            logger.out("ImageUtils original this image IOException", e);
        }
    }

    /**
     * 获得图片宽度
     *
     * @param file 图片文件
     * @return
     */
    public static int getWidth(File file) {
        try {
            Image src = ImageIO.read(file);
            return src.getWidth(null);
        } catch (IOException e) {
            logger.out(e);
        }
        return 0;
    }

    /**
     * 获得图片高度
     *
     * @param file 图片文件
     * @return
     */
    public static int getHeight(File file) {
        try {
            Image src = ImageIO.read(file);
            return src.getHeight(null);
        } catch (IOException e) {
            logger.out(e);
        }
        return 0;
    }

    /**
     * 图片切割
     *
     * @param targetFile 新图片地址
     * @param x          目标切片起点x坐标
     * @param y          目标切片起点y坐标
     * @param width      目标切片宽度
     * @param height     目标切片高度
     */
    public static void cut(InputStream in, File targetFile, int x, int y, int width, int height) {
        try {
            Image img;
            ImageFilter cropFilter;
            // 读取源图像
            BufferedImage bi = ImageIO.read(in);
            int srcWidth = bi.getWidth(); // 源图宽度
            int srcHeight = bi.getHeight(); // 源图高度
            if (srcWidth >= width && srcHeight >= height) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                cropFilter = new CropImageFilter(x, y, width, height);
                img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, "PNG", targetFile);
            } else {
                ImageIO.write(bi, "PNG", targetFile);
            }
        } catch (Exception e) {
            logger.out(e);
        }
    }

}
