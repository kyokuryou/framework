package org.smarty.core.net;

import org.junit.Test;
import org.smarty.core.support.net.SocketMonitor;
import org.smarty.core.test.AbsTestCase;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * socketClient测试
 */
public class SocketClientTest extends AbsTestCase {
    /**
     * 向服务端发送文件
     */
    @Test
    public void testFileClient() throws UnknownHostException {
        // 打开连接
//        SocketMonitor sc = new SocketMonitor("172.17.36.40");
//        try {
//            // 向目标服务端8080端口发出请求
//            Socket s = sc.openSocket(8080);
//            // 打开文件
//            FileInputStream fis = new FileInputStream("f:/dky_webservice.rar");
//            // 发送文件
//            sc.sendFile(s, fis);
//            // 关闭连接
//            s.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 向目标服务器发送文本
     */
    @Test
    public void testPlanClient() {
        // 打开连接
//        SocketClient sc = new SocketClient("172.17.36.40");
//        try {
//            // 向目标服务端8080端口发出请求
//            Socket s = sc.openSocket(8080);
//            // 发送文本
//            sc.sendPlain(s, "hello word");
//            // 关闭连接
//            s.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
