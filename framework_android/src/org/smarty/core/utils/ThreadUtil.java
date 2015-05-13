package org.smarty.core.utils;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 线程工具
 */
public final class ThreadUtil {
    private static final Map<String, HandlerThread> threadMap = new ConcurrentHashMap<String, HandlerThread>();

    public static HandlerThread getHandlerThread(String name) {
        HandlerThread thread = threadMap.get(name);
        if (thread == null || !thread.isAlive()) {
            thread = new HandlerThread(name);
            threadMap.put(name, thread);
        }
        return thread;
    }

    public static Looper getLooper(String name) {
        HandlerThread thread = getHandlerThread(name);
        tryStart(thread);
        return thread.getLooper();
    }

    public static Handler getHandler(String name, Callback callback) {
        Looper looper = getLooper(name);
        if (callback != null) {
            return new Handler(looper, callback);
        }
        return new Handler(looper);
    }

    public static void destroyThread(String name) {
        HandlerThread thread = threadMap.get(name);
        if (thread == null) {
            return;
        }
        tryStop(thread);
    }

    private static void tryStart(HandlerThread thread) {
        if (thread != null && !thread.isAlive()) {
            thread.start();
        }
    }

    private static void tryStop(HandlerThread thread) {
        if (thread != null && thread.isAlive()) {
            thread.quit();
        }
    }
}
