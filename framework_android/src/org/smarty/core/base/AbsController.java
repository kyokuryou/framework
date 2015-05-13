package org.smarty.core.base;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import org.smarty.core.commons.ReceiverManager;
import org.smarty.core.utils.ThreadUtil;

/**
 * activity控制器实现
 */
public abstract class AbsController implements IController {
    protected final String TAG = getClass().getSimpleName();
    private final String SERVICE_THREAD_NAME = "serviceHandler";
    protected Context context;
    protected ReceiverManager receiverManager;

    @Override
    public void afterPropertiesSet(Context context) {
        this.context = context;
        this.receiverManager = new ReceiverManager(context);
    }

    @Override
    public void destroy() {
        try {
            ThreadUtil.destroyThread(SERVICE_THREAD_NAME);
        } catch (RuntimeException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public Context getApplicationContext() {
        return context.getApplicationContext();
    }

    protected final void startService(final ServiceHandler sh) {
        Handler handler = ThreadUtil.getHandler(SERVICE_THREAD_NAME, null);
        handler.post(new Runnable() {
            @Override
            public void run() {
                context.startService(sh.createService());
            }
        });
    }


    protected interface ServiceHandler {
        public Intent createService();
    }
}
