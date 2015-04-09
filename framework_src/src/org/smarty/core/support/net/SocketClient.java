package org.smarty.core.support.net;

import java.io.IOException;
import java.net.Socket;

/**
 * 网络适配器(客户端)
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
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
