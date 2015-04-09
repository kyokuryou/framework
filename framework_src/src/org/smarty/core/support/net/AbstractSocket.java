package org.smarty.core.support.net;

import org.smarty.core.logger.RuntimeLogger;
import org.smarty.core.utils.CommonUtil;
import org.smarty.core.utils.LogicUtil;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 网络适配器
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public abstract class AbstractSocket {
    private static RuntimeLogger logger = new RuntimeLogger(AbstractSocket.class);
    // 当前线程变量
    protected final ThreadLocal<InetAddress> tl = new ThreadLocal<InetAddress>() {
        public synchronized InetAddress get() {
            return super.get();
        }

        public synchronized void set(InetAddress value) {
            super.set(value);
        }
    };
    private final int maxSize = 1024;


    /**
     * 初始化本地连接嵌套字
     */
    protected AbstractSocket() {
        try {
            tl.set(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            logger.out(e);
        }
    }

    /**
     * 初始化指定连接嵌套字
     *
     * @param host 嵌套字
     */
    protected AbstractSocket(String host) {
        try {
            tl.set(InetAddress.getByName(host));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送一段文本
     *
     * @param so   连接
     * @param text text
     */
    public void sendPlain(Socket so, String text) throws IOException {
        if (so.isClosed()) {
            return;
        }
        BufferedOutputStream dos = new BufferedOutputStream(so.getOutputStream());
        ByteArrayInputStream bis = new ByteArrayInputStream(text.getBytes());
        readAndWrite(bis, dos);
        bis.close();
        dos.flush();
        dos.close();
    }

    /**
     * 接收一段文本
     *
     * @param so 连接
     * @throws java.io.IOException
     */
    public String receivePlain(Socket so) throws IOException {
        if (so.isClosed()) {
            return null;
        }
        BufferedInputStream dis = new BufferedInputStream(so.getInputStream());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int data = 0;
        while ((data = dis.read()) != -1) {
            bos.write(data);
        }
        dis.close();
        return bos.toString();
    }

    /**
     * 发送文件,发送完成之后,会自动关闭文件输入流
     *
     * @param so 连接
     * @param is 文件流
     * @throws java.io.IOException
     */
    public void sendFile(Socket so, InputStream is) throws IOException {
        if (so.isClosed()) {
            return;
        }
        DataOutputStream dos = new DataOutputStream(so.getOutputStream());
        try {
            readAndWrite(is, dos);
        } finally {
            dos.flush();
            dos.close();
        }
    }

    /**
     * 发送文件,发送完成之后,会自动关闭文件输入流
     *
     * @param so   连接
     * @param file 文件
     * @throws java.io.IOException
     */
    public void sendFile(Socket so, File file) throws IOException {
        if (so.isClosed() || file == null || !file.isFile()) {
            return;
        }
        FileInputStream fis = new FileInputStream(file);
        DataOutputStream dos = new DataOutputStream(so.getOutputStream());
        try {
            // 发送文件后缀
            dos.write(getFileExt(file));
            // 发送文件内容
            readAndWrite(fis, dos);
        } finally {
            dos.flush();
            dos.close();
            fis.close();
        }
    }

    /**
     * 接收文件,接收文件之后会自动关闭文件输出流
     *
     * @param so 连接
     * @param os 文件流
     * @throws java.io.IOException
     */
    public void receiveFile(Socket so, OutputStream os) throws IOException {
        if (so.isClosed()) {
            return;
        }

        DataInputStream dis = new DataInputStream(so.getInputStream());
        int data = 0;
        while ((data = dis.read()) != -1) {
            os.write(data);
        }
        dis.close();
    }

    /**
     * 接收文件,接收文件之后会自动关闭文件输出流
     *
     * @param so   连接
     * @param file 路径
     * @throws java.io.IOException
     */
    public void receiveFile(Socket so, File file) throws IOException {
        if (so.isClosed() || file == null) {
            return;
        }
        DataInputStream dis = new DataInputStream(so.getInputStream());
        FileOutputStream fos = new FileOutputStream(createFileName(dis, file));

        int data = 0;
        while ((data = dis.read()) != -1) {
            fos.write(data);
        }
        dis.close();
        fos.flush();
        fos.close();
    }

    /**
     * 创建文件
     *
     * @param is   输入流
     * @param file 文件路径
     * @return 文件描述字符串
     * @throws java.io.IOException
     */
    private String createFileName(InputStream is, File file) throws IOException {
        byte[] bs = new byte[15];
        StringBuilder fileName = new StringBuilder(file.getPath());
        fileName.append("/").append(CommonUtil.getDateRandom(5));
        fileName.append(".");
        if (is.read(bs, 0, 15) != -1) {
            fileName.append(new String(bs));
        }
        return fileName.toString();
    }

    /**
     * 从输入流中读取某组数据
     *
     * @param is    输入流
     * @param group 组编号
     * @return 数据
     * @throws java.io.IOException
     */
    private byte[] readBytes(InputStream is, int group) throws IOException {
        int a = is.available();
        if (a == 0) {
            return null;
        }
        int off = group * maxSize;
        int len = a > maxSize ? maxSize : a;

        byte[] bytes = new byte[len + off];
        if (is.read(bytes, off, len) != -1) {
            return (byte[]) CommonUtil.copyArray(bytes, off, bytes.length);
        }
        return null;
    }

    /**
     * 输入流数据复制到输出流中
     *
     * @param is 输入流
     * @param os 输出流
     * @throws java.io.IOException
     */
    private void readAndWrite(InputStream is, OutputStream os) throws IOException {
        for (int i = 0; ; i++) {
            byte[] bs = readBytes(is, i);
            if (LogicUtil.isEmptyArray(bs)) {
                break;
            }
            os.write(bs);
        }
    }

    /**
     * 获取文件后缀
     *
     * @param file 文件
     * @return 文件后缀字节
     * @throws java.io.IOException
     */
    private byte[] getFileExt(File file) throws IOException {
        // 获取文件后缀
        String fExt = CommonUtil.getExt(file.getName());
        // 发送前10个字节为文件后缀
        if (fExt.length() > 15) {
            throw new IOException("文件后缀超过指定大小.");
        }
        return (byte[]) CommonUtil.copyArray(fExt.getBytes(), 0, 15);
    }

}
