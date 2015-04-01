package org.smarty.core.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: kyokuryou
 * Date: 13-11-4
 * Time: 上午8:43
 * To change this template use File | Settings | File Templates.
 */
public class SocketServerTestter {
    private static final int maxSize = 1024;

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(12523);
        ss.setReceiveBufferSize(10240);
        Socket s = ss.accept();
        s.setKeepAlive(true);
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(new FileOutputStream("f:/test_back.txt"));
        outputFile(dis, dos);
        dis.close();
        dos.flush();
        dos.close();
        s.close();
    }

    private static void outputFile(InputStream is, OutputStream os) throws IOException {
        if(is == null || os == null){
            return ;
        }
        int data = 0;
        while ((data = is.read()) != -1) {
            os.write(data);
        }
    }

    public static void main1(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8090);
        Socket s = ss.accept();
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(new FileOutputStream("f:/test_back.txt"));
        int d = 0;
        while ((d = dis.read()) != -1) {
            dos.write(d);
        }
        dis.close();
        dos.flush();
        dos.close();
        s.close();
    }
}
