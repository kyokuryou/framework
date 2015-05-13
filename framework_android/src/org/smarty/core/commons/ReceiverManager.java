package org.smarty.core.commons;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import org.smarty.core.base.BasicReceiver;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class ReceiverManager {
    private final String TAG = "Register";
    private final Context context;
    private final Map<String, BasicReceiver> receiverMap = new ConcurrentHashMap<String, BasicReceiver>();
    private final HandlerThread receiverThread = new HandlerThread("receiverThread");

    public ReceiverManager(Context context) {
        this.context = context;
        if (receiverThread.isAlive()) {
            return;
        }
        try {
            receiverThread.start();
        } catch (RuntimeException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 解除所有广播
     */
    public void unAllReceive() {
        if (receiverMap.isEmpty()) {
            return;
        }
        Set<String> names = receiverMap.keySet();
        for (String name : names) {
            unReceive(name);
        }
    }

    /**
     * 解除广播
     *
     * @param name 广播名称
     */
    public void unReceive(String name) {
        if (name == null || "".equals(name) || !receiverMap.containsKey(name)) {
            return;
        }
        context.unregisterReceiver(receiverMap.get(name));
        receiverMap.remove(name);
    }

    /**
     * 注册广播
     *
     * @param name    广播名称
     * @param receive 广播
     */
    public void receive(String name, BasicReceiver receive) {
        Assert.notNull(receive, "receive must not be null");
        if (name == null || "".equals(name)) {
            return;
        }
        IntentFilter filter = receive.getIntentFilter();
        if (filter == null) {
            filter = new IntentFilter();
        }
        context.registerReceiver(receive, filter);
        receiverMap.put(name, receive);
    }

    /**
     * 开启线程注册广播
     *
     * @param name    广播名称
     * @param receive 广播
     */
    public void receiveHandler(final String name, final BasicReceiver receive) {
        Handler handler = new Handler(receiverThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                receive(name, receive);
            }
        });
    }

    /**
     * 注册匿名广播
     *
     * @param receive 广播
     * @return 广播名称
     */
    public String anonymousReceive(BasicReceiver receive) {
        String name = getUUID();
        receive(name, receive);
        return name;
    }

    public void onStop() {
        try {
            receiverThread.quit();
        } catch (RuntimeException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private String getUUID() {
        String s = UUID.randomUUID().toString();
        return s.replaceAll("[-]", "");
    }
}
