package org.core.test;

import edu.emory.mathcs.backport.java.util.Arrays;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: kyokuryou
 * Date: 13-11-4
 * Time: 上午8:43
 * To change this template use File | Settings | File Templates.
 */
public class SocketClientTestter {
    private static final int maxSize = 1024;

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("172.17.36.40", 12523);
        s.setSendBufferSize(maxSize);
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        DataInputStream dis = new DataInputStream(new FileInputStream("f:/test.txt"));
        for (int i = 0; ; i++) {
            byte[] bs = inputByteList(dis, i);
            if (bs == null || bs.length == 0) {
                break;
            }
            outputByteList(dos, bs, i);
            dos.flush();
        }
        dis.close();
        dos.flush();
        dos.close();
        s.close();
    }

    private static byte[] inputByteList(InputStream is, int group) throws IOException {
        int a = is.available();
        if (a == 0) {
            return null;
        }
        int off = group * maxSize;
        int len = a > maxSize ? maxSize : a;

        byte[] bytes = new byte[len + off];
        if (is.read(bytes, off, len) != -1) {
            return bytes;
        }
        return null;
    }

    private static void outputByteList(OutputStream os, byte[] bs, int group) throws IOException {
        if (bs == null || bs.length == 0) {
            return;
        }
        int off = group * maxSize;
        os.write(Arrays.copyOfRange(bs, off, bs.length));
    }
}
