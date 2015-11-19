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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
     * @param image
     * @return
     */
    public static String getImageRealType(File image) {
        byte[] b = new byte[4];
        FileInputStream is = null;
        try {
            is = new FileInputStream(image);
            is.read(b, 0, b.length);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
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

    /**
     * 根据文件流读取图片文件真实类型
     *
     * @param in
     * @return
     */
    public static String getImageRealType(InputStream in) {
        byte[] b = new byte[4];
        try {
            in.read(b, 0, b.length);
        } catch (IOException e) {
            e.printStackTrace();
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

    /**
     * 缩放图片
     *
     * @param oldFile    原图片
     * @param formatName 缩放后格式
     * @param newFile    缩放后图片
     * @param width      指定宽度
     * @param height     指定高度
     * @param db         是否等比例缩放
     * @throws java.io.IOException
     */
    public static void resizeImage(File oldFile, String formatName, File newFile, int width, int height, boolean db) throws IOException {
        if (!(oldFile != null && oldFile.isFile() && oldFile.exists()) || newFile == null) {
            return;
        }
        try {
            BufferedImage image = ImageIO.read(oldFile);
            double[] zoomSize = new double[2];
            double srcWidth = image.getWidth();
            double srcHeigth = image.getHeight();

            if (db) {
                // 文件宽和高都小于指定宽和高则不需要处理
                // if (srcWidth <= width && srcHeigth <= height) {
                // // 不缩放
                // zoomSize[0] = srcWidth;
                // zoomSize[1] = srcHeigth;
                // } else {
                // 等比例缩放控制
                double tempHeight = (srcHeigth / srcWidth) * width;
                if (tempHeight > height) {
                    zoomSize[0] = (srcWidth / srcHeigth) * height;
                    zoomSize[1] = height;
                } else {
                    zoomSize[0] = width;
                    zoomSize[1] = (srcHeigth / srcWidth) * width;
                }
                // }
            } else {// 不等比例
                zoomSize[0] = width;
                zoomSize[1] = height;
            }
            ResampleOp resampleOp = new ResampleOp((int) zoomSize[0],
                    (int) zoomSize[1]);
            BufferedImage tag = resampleOp.filter(image, null);
            ImageIO.write(tag, formatName, newFile);
        } catch (IOException e) {
            logger.out("ImageUtils resizeImage this image IOException", e);
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
     * @param intImageFile 源图像地址
     * @param outImageFile 新图片地址
     * @param x            目标切片起点x坐标
     * @param y            目标切片起点y坐标
     * @param destWidth    目标切片宽度
     * @param destHeight   目标切片高度
     */
    public static void cut(String intImageFile, String outImageFile, int x,
                           int y, int destWidth, int destHeight) {
        try {
            Image img;
            ImageFilter cropFilter;
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(intImageFile));
            int srcWidth = bi.getWidth(); // 源图宽度
            int srcHeight = bi.getHeight(); // 源图高度
            if (srcWidth >= destWidth && srcHeight >= destHeight) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight,
                        Image.SCALE_DEFAULT);
                cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
                img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(destWidth, destHeight,
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, "JPEG", new File(outImageFile));
            } else {
                ImageIO.write(bi, "JPEG", new File(outImageFile));
            }
        } catch (Exception e) {
            logger.out(e);
        }
    }

}
