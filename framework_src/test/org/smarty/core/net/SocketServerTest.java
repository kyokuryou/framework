package org.smarty.core.net;

import org.smarty.core.support.net.SocketServer;
import org.smarty.core.test.AbsTestCase;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * socketServer测试
 */
public class SocketServerTest extends AbsTestCase {
    /**
     * 服务器文件监听
     */
    @Test
    public void testFileServer() {
        SocketServer ss = new SocketServer();
        // 打开8080端口监听
        ss.monitorSocket(8080);
        // 打开8081端口监听
        ss.monitorSocket(8081);
        // 打开8082端口监听
        ss.monitorSocket(8082);
        // 是否有可用的连接
        while (ss.hasNext()) {
            // 获得一个连接
            Socket s = ss.next();
            try {
                // 打开文件
                FileOutputStream fos = new FileOutputStream("f:/dky.rar");
                // 接收文件
                ss.receiveFile(s, fos);
                // 关闭连接
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 服务器文件监听
     */
    @Test
    public void testPlainServer() {
        SocketServer ss = new SocketServer();
        // 打开8080端口监听
        ss.monitorSocket(8080);
        // 打开8081端口监听
        ss.monitorSocket(8081);
        // 打开8082端口监听
        ss.monitorSocket(8082);
        // 是否有可用的连接
        while (ss.hasNext()) {
            // 获得一个连接
            Socket s = ss.next();
            try {
                // 接收文本
                String plain = ss.receivePlain(s);
                // 输出控制台
                System.out.println(plain);
                // 关闭连接
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 服务器多线程文本监听
     */
    @Test
    public void testPlainServerThread() {
        final SocketServer ss = new SocketServer();
        // 打开8080端口监听
        ss.monitorSocket(8080);
        // 打开8081端口监听
        ss.monitorSocket(8081);
        // 打开8082端口监听
        ss.monitorSocket(8082);
        // 是否有可用的连接
        while (ss.hasNext()) {
            // 获得一个连接
            Socket s = ss.next();
            // 以匿名形式实例化ServerThread
            new ServerThread(s) {
                public void runSocket(Socket socket) {
                    try {
                        // 接收文本
                        String plain = ss.receivePlain(socket);
                        // 输出控制台
                        System.out.println(plain);
                        // 关闭连接
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    /**
     * 服务器多线程文件监听
     */
    @Test
    public void testFileServerThread() {
        final SocketServer ss = new SocketServer();
        // 打开8080端口监听
        ss.monitorSocket(8080);
        // 打开8081端口监听
        ss.monitorSocket(8081);
        // 打开8082端口监听
        ss.monitorSocket(8082);
        // 是否有可用的连接
        while (ss.hasNext()) {
            // 获得一个连接
            Socket s = ss.next();
            // 以匿名形式实例化ServerThread
            new ServerThread(s) {
                public void runSocket(Socket socket) {
                    try {
                        // 打开文件
                        FileOutputStream fos = new FileOutputStream("f:/dky.rar");
                        // 接收文件
                        ss.receiveFile(socket, fos);
                        // 关闭连接
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    private abstract class ServerThread extends Thread{
        private Socket socket;
        public ServerThread(Socket socket){
            this.socket = socket;
        }

        public void run() {
            runSocket(socket);
        }

        public abstract void runSocket(Socket socket);
    }
}
