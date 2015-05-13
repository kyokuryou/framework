package org.smarty.core.support.net;

import org.smarty.core.logger.RuntimeLogger;
import org.springframework.core.task.TaskExecutor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 网络适配器(服务端)
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class SocketServer extends AbstractSocket {
    private static RuntimeLogger logger = new RuntimeLogger(SocketServer.class);

    private TaskExecutor taskExecutor;

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    /**
     * 打开监听
     *
     * @param sm SocketMonitor
     */
    public void addMonitor(SocketMonitor sm) {
        if (sm == null) {
            return;
        }
        try {
            ServerSocket ss = new ServerSocket(sm.getPort(), 50, sm.getInetAddress());
            taskExecutor.execute(new MonitorThread(ss, sm));
        } catch (IOException e) {
            logger.out(e);
        }
    }

    /**
     * 异步监听
     */
    private class MonitorThread implements Runnable {
        private ServerSocket ss;
        private SocketMonitor sm;

        private MonitorThread(ServerSocket ss, SocketMonitor sm) {
            if (ss == null) {
                throw new NullPointerException();
            }
            this.ss = ss;
            this.sm = sm;
        }

        public void run() {
            try {
                Socket so = ss.accept();
                sm.acceptSocket(so);
            } catch (IOException e) {
                logger.out(e);
            } finally {
                interrupt();
            }
        }

        public void interrupt() {
            try {
                if (!ss.isClosed()) {
                    ss.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
