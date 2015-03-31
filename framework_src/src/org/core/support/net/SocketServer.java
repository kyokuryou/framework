package org.core.support.net;

import org.core.logger.RuntimeLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 网络驱动堆
 */
public class SocketServer extends AbstractSocket {
    private static RuntimeLogger logger = new RuntimeLogger(SocketServer.class);

    // 线程组
    private final ThreadGroup tg = new ThreadGroup("1");
    // 互斥锁
    private final ReentrantLock lock = new ReentrantLock();
    // 状态
    private boolean isClosed = false;
    // 链接
    private LinkedList<Socket> socketQueue;
    // 链接容器内最大链接数量
    private static final int maxConnection = 5;


    /**
     * 初始化本地连接嵌套字
     */
    public SocketServer() {
        super();
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //设置是否守护线程池
        tg.setDaemon(true);
        socketQueue = new LinkedList<Socket>() {
            @Override
            public int size() {
                for (int i = 0, len = super.size(); i < len; ) {
                    Socket so = get(i);
                    if (so == null || so.isClosed()) {
                        remove(i);
                        len = super.size();
                    } else {
                        i++;
                    }
                }
                return super.size();
            }
        };
    }

    /**
     * 打开监听
     *
     * @param port 端口号
     */
    public void monitorSocket(int port) {
        try {
            ServerSocket ss = new ServerSocket(port, 50, tl.get());
            new MonitorThread(ss).start();
        } catch (IOException e) {
            logger.out(e);
        }
    }

    /**
     * 放入请求池
     *
     * @param so 连接
     * @throws InterruptedException
     */
    private synchronized void putSocket(Socket so) throws InterruptedException {
        if (isClosed) {
            throw new IllegalStateException();
        }
        if (socketQueue.size() > maxConnection) {
            // 超过请求池内最大阀值
            wait();
        }
        if (so != null) {
            socketQueue.add(so);
            //唤醒一个正在hasNext()方法中待任务的工作线程
            notify();
        }
    }

    /**
     * 如果仍有连接可以迭代，则返回 true。
     * **当请求池中没有连接时,该方法与next,remove方法处于暂停状态,直到连接池中有连接或关闭监听时,继续**
     *
     * @return 如果迭代器具有多个元素，则返回 true。
     */
    public synchronized boolean hasNext() {
        lock.lock();
        while (socketQueue.size() == 0) {
            if (isClosed) break;
            try {
                wait();
            } catch (InterruptedException e) {
                logger.out(e);
                lock.unlock();
                return false;
            }
        }
        return socketQueue.size() > 0;
    }

    /**
     * 返回迭代的下一个元素。
     * ** 此方法与hasNext()是联锁状态,不允许不通过hasNext判断执行该方法.**
     *
     * @return 迭代的下一个元素。
     */
    public synchronized Socket next() {
        notify();
        if (!lock.isLocked()) {
            return null;
        }
        lock.unlock();
        return socketQueue.removeFirst();
    }


    /**
     * 关闭监听
     */
    public synchronized void closeMonitor() {
        if (!isClosed) {
            //等待工作线程执行完毕
            waitFinish();
            //中断线程池中的所有的工作线程,此方法继承自ThreadGroup类
            tg.interrupt();
        }
    }

    /**
     * 等待监听线程结束
     */
    public void waitFinish() {
        synchronized (this) {
            isClosed = true;
            //唤醒所有还在hasNext()方法中等待
            notifyAll();
        }
    }

    /**
     * 异步监听
     */
    private class MonitorThread extends Thread {
        private ServerSocket ss;

        private MonitorThread(ServerSocket ss) {
            super(tg, "Thread-Monitor");
            if (ss == null) {
                throw new NullPointerException();
            }
            this.ss = ss;
        }

        public void run() {
            //isInterrupted()方法继承自Thread类，判断线程是否被中断
            while (!isInterrupted() && !isClosed) {
                try {
                    putSocket(ss.accept());
                } catch (IOException e) {
                    logger.out(e);
                } catch (InterruptedException e) {
                    logger.out(e);
                }
            }
        }

        @Override
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
