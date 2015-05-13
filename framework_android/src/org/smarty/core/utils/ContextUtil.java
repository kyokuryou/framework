package org.smarty.core.utils;

import android.app.Application;
import android.content.Context;

/**
 * ContextUtil
 */
public final class ContextUtil extends Application {

    private static ContextUtil context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }

}
