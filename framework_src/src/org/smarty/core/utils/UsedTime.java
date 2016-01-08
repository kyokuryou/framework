package org.smarty.core.utils;

import org.smarty.core.io.RuntimeLogger;

import java.util.Stack;

/**
 * 测试一段过程消耗的时间, 线程安全的类, 无需实例
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public final class UsedTime {
    private static RuntimeLogger logger = new RuntimeLogger(UsedTime.class);
    private static ThreadLocal<Stack<Time>> times = new ThreadLocal<Stack<Time>>();
    private static final String NULL_STR = "";

    private UsedTime() {
    }

    /**
     * 开始计时
     */
    public static void start() {
        start(NULL_STR);
    }

    /**
     * 开始计时,并赋予一个描述
     *
     * @param desc - 对此次计时的描述
     */
    public static void start(String desc) {
        Stack<Time> q = times.get();

        if (q == null) {
            q = new Stack<Time>();
            times.set(q);
        }

        Time t = new Time();
        t.setName(desc);
        t.reset();

        q.push(t);
    }

    /**
     * 结束计时,并返回start与end调用之间,经过的时间<br>
     * 调用此方法会影响结果,所以如果需要多次取得结果,应使用getUsedTime
     */
    public static long end() {
        Stack<Time> q = times.get();
        Time t = q.pop();
        // Tools.check(t, "错误: 尚未调用start()");
        t.end();

        return t.usedTime();
    }

    /**
     * 返回上次start()与end()调用之间,经过的时间 该方法必须在end(),endAndPrint()之前调用有效
     */
    public static long getUsedTime() {
        Stack<Time> q = times.get();
        Time t = q.peek();
        // Tools.check(t, "错误: 尚未调用start()");
        return t.usedTime();
    }

    /**
     * 停止计时,并且在控制台输出使用的时间
     */
    public static void endAndPrint() {
        endAndPrint(System.out);
    }

    /**
     * 停止计时,并且out输出使用的时间
     */
    public static void endAndPrint(Appendable out) {
        Stack<Time> q = times.get();
        Time t = q.pop();
        // Tools.check(t, "错误: 尚未调用start()");
        t.end();

        try {
            out.append(t.getName());
            out.append("使用了: ");
            out.append(Long.toString(t.usedTime()));
            out.append(" ms");
        } catch (Exception e) {
            logger.out(e);
        }
    }

    public static void printAll() {
        Stack<Time> q = times.get();

        while (!q.empty()) {
            Time t = q.pop();
            t.end();
            // Tools.pl(t.getName() + "使用了: " + t.usedTime() + " ms");
        }
    }

    private static class Time {
        private long startT;
        private long endT;
        private String name;

        private Time() {
            name = NULL_STR;
            reset();
        }

        public void reset() {
            startT = System.currentTimeMillis();
            endT = startT;
        }

        public void end() {
            endT = System.currentTimeMillis();
        }

        public long usedTime() {
            return endT - startT;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
