package org.core.support.net;

import java.io.IOException;
import java.net.Socket;

/**
 * 网络驱动堆
 */
public class SocketClient extends AbstractSocket {

    public SocketClient(String host) {
        super(host);
    }

    /**
     * 打开一个请求连接
     *
     * @param port 端口号
     * @return 连接
     * @throws java.io.IOException
     */
    public Socket openSocket(int port) throws IOException {
        try {
            return new Socket(tl.get(), port);
        } catch (IOException e) {
            throw e;
        }
    }
}
