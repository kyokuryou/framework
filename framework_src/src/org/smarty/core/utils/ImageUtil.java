package org.smarty.core.utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.SampleModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 图片处理工具类(该类对图片的处理基于imagescaling第三方组件)
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class ImageUtil {
    private static Log logger = LogFactory.getLog(ImageUtil.class);

    private ImageUtil() {
    }

    /**
     * 根据文件流读取图片文件真实类型
     *
     * @param data
     * @return
     */
    public static String getRealType(byte[] data) throws IOException {
        if (data == null || data.length == 0) {
            return null;
        }
        byte[] b = Arrays.copyOf(data, 4);

        String type = FileUtil.bytesToHexString(b).toUpperCase();
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
     * 输出原图
     *
     * @param data     字节数据
     * @param compress 是否压缩
     * @throws IOException
     */
    public static BufferedImage original(byte[] data, boolean compress) throws IOException {
        if (data == null || data.length == 0) {
            return null;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ImageInputStream iis = ImageIO.createImageInputStream(bais);
        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
        if (!iter.hasNext()) {
            return null;
        }
        ImageReader reader = iter.next();
        ImageReadParam param = reader.getDefaultReadParam();
        String fname = reader.getFormatName();
        reader.setInput(iis, true, true);

        BufferedImage image = reader.read(0, param);
        if (compress) {
            if ("JPEG".equalsIgnoreCase(fname)) {
                image = compressJPG(image, 0.5f);
            } else if ("PNG".equalsIgnoreCase(fname)) {
                image = compressPNG(image);
            }
        }
        return image;
    }

    /**
     * 缩放/放大图片
     *
     * @param image  原图
     * @param width  宽
     * @param height 高
     * @param db     是否等比缩放
     * @throws IOException
     */
    public static BufferedImage resize(BufferedImage image, int width, int height, boolean db) throws IOException {
        int srcWidth = image.getWidth();
        int srcHeigth = image.getHeight();
        double zoomWidth = width * 1.0f / srcWidth;
        double zoomHeight = height * 1.0f / srcHeigth;
        if (db) {
            // 等比例缩放
            double tempHeight = (srcHeigth / srcWidth) * width;
            if (tempHeight > height) {
                zoomWidth = ((srcWidth / srcHeigth) * height) * 1.0f / srcWidth;
            } else {
                zoomHeight = tempHeight / srcHeigth;
            }
        }
        AffineTransform atf = AffineTransform.getScaleInstance(zoomWidth, zoomHeight);
        AffineTransformOp ato = new AffineTransformOp(atf, null);
        return ato.filter(image, null);
    }

    /**
     * 图片切割
     *
     * @param x      目标切片起点x坐标
     * @param y      目标切片起点y坐标
     * @param width  目标切片宽度
     * @param height 目标切片高度
     */
    public static BufferedImage cut(BufferedImage image, int x, int y, int width, int height) {
        // 读取源图像
        int srcWidth = image.getWidth(); // 源图宽度
        int srcHeight = image.getHeight(); // 源图高度
        if (srcWidth >= width && srcHeight >= height) {
            Image img = image.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
            ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
            img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(img.getSource(), cropFilter));
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(img, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            // 输出为文件
            return tag;
        }
        return image;
    }

    public static BufferedImage compressPNG(BufferedImage image) throws IOException {
        if (image == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int width = image.getWidth();
        int height = image.getHeight();
        //TYPE_USHORT_555_RGB，可以修改像素的布尔透明
        BufferedImage convertedImage = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_555_RGB);
        Graphics2D g2D = (Graphics2D) convertedImage.getGraphics();
        g2D.drawImage(image, 0, 0, null);
        g2D.drawImage(convertedImage, 0, 0, null);
        try {
            ImageIO.write(convertedImage, "PNG", out);
        } finally {
            g2D.dispose();
        }
        return toBufferedImage(out.toByteArray());
    }


    /**
     * 自己设置压缩质量来把图片压缩成byte[]
     *
     * @param image   压缩源图片
     * @param quality 压缩质量，在0-1之间，
     * @return 返回的字节数组
     */
    public static BufferedImage compressJPG(BufferedImage image, float quality) throws IOException {
        if (image == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("JPEG");
        ImageWriter writer = it.next();

        ImageWriteParam iwp = writer.getDefaultWriteParam();
        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        iwp.setCompressionQuality(quality);
        iwp.setProgressiveMode(ImageWriteParam.MODE_DISABLED);

        ColorModel cm = ColorModel.getRGBdefault();
        SampleModel sm = cm.createCompatibleSampleModel(16, 16);
        iwp.setDestinationType(new ImageTypeSpecifier(cm, sm));

        IIOImage img = new IIOImage(image, null, null);
        try {
            writer.setOutput(ImageIO.createImageOutputStream(out));
            writer.write(null, img, iwp);
        } finally {
            writer.dispose();
        }
        return toBufferedImage(out.toByteArray());
    }


    public static String getFormatName(byte[] data) throws IOException {
        if (data == null || data.length == 0) {
            return null;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ImageInputStream iis = ImageIO.createImageInputStream(bais);
        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
        if (!iter.hasNext()) {
            return null;
        }

        ImageReader reader = iter.next();
        return reader.getFormatName();
    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = iis.read(b)) != -1) {
            baos.write(b, 0, len);
        }
        return baos.toByteArray();
    }

    private static BufferedImage toBufferedImage(byte[] data) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(data));
    }

    public static void outImage(BufferedImage image, String formatName, File file) throws IOException {
        ImageIO.write(image, formatName, file);
    }
}
