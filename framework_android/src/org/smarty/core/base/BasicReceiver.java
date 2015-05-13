package org.smarty.core.base;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

/**
 * 针对Receiver扩展类
 */
public abstract class BasicReceiver extends BroadcastReceiver {
    public abstract IntentFilter getIntentFilter();

}
