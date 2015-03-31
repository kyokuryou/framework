package org.core.common;

import org.core.logger.RuntimeLogger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 网络驱动堆
 */
public class SocketStack extends ThreadGroup {
    private static final int threadPoolID = 1;  //线程的id
    private static RuntimeLogger logger = new RuntimeLogger(SocketStack.class);
    // 互斥锁
    private final ReentrantLock lock = new ReentrantLock();
    // 当前线程变量
    private ThreadLocal<InetAddress> tl = new ThreadLocal<InetAddress>();
    // 状态
    private boolean isClosed = false;
    // 链接
    private LinkedList<Socket> socketQueue;
    // 链接容器内最大链接数量
    private static final int maxConnection = 5;


    /**
     * 初始化本地连接嵌套字
     */
    public SocketStack() {
        super(threadPoolID + "");
        try {
            init(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            logger.out(e);
        }
    }
    /**
     * 初始化指定连接嵌套字
     * @param host 嵌套字
     */
    public SocketStack(String host) {
        super(threadPoolID + "");
        try {
            init(InetAddress.getByName(host));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化
     *
     * @param ia 协议地址
     */
    private void init(InetAddress ia) {
        //继承到的方法，设置是否守护线程池
        setDaemon(true);
        tl.set(ia);
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
            logger.out(e);
            throw e;
        }
    }

    /**
     * 请求发送
     *
     * @param so 连接
     * @return
     * @throws java.io.IOException
     */
    public Writer getWriter(Socket so) throws IOException {
        if (so.isClosed()) {
            return null;
        }
        try {
            return new OutputStreamWriter(so.getOutputStream());
        } catch (IOException e) {
            logger.out(e);
            throw e;
        }
    }

    /**
     * 响应接收
     *
     * @param so 连接
     * @return
     * @throws java.io.IOException
     */
    public Reader getReader(Socket so) throws IOException {
        if (so.isClosed()) {
            return null;
        }
        try {
            return new InputStreamReader(so.getInputStream());
        } catch (IOException e) {
            logger.out(e);
            throw e;
        }
    }

    /**
     * 关闭连接
     * @param so 连接
     * @throws java.io.IOException
     */
    public void closeSocket(Socket so) throws IOException {
        if (so.isClosed()) {
            return;
        }
        try {
            so.close();
        } catch (IOException e) {
            logger.out(e);
            throw e;
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
            if (isClosed) return false;
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
            interrupt();
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
        //该线程组中活动线程的估计值。
        Thread[] threads = new Thread[activeCount()];
        // 根据活动线程的估计值获得线程组中当前所有活动的工作线程
        int count = enumerate(threads);
        //等待监听线程结束
        for (int i = 0; i < count; i++) {
            try {
                //等待工作线程结束
                threads[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 异步监听
     */
    private class MonitorThread extends Thread {
        private ServerSocket ss;

        private MonitorThread(ServerSocket ss) {
            if (ss == null) {
                throw new NullPointerException();
            }
            this.ss = ss;
        }

        public void run() {
            //isInterrupted()方法继承自Thread类，判断线程是否被中断
            while (!isInterrupted()) {
                try {
                    putSocket(ss.accept());
                } catch (IOException e) {
                    logger.out(e);
                } catch (InterruptedException e) {
                    logger.out(e);
                }
            }
        }

    }
}
