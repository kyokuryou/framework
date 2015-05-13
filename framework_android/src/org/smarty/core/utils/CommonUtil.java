package org.smarty.core.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

/**
 * CommonUtil
 */
public final class CommonUtil {
    private static Context context;

    public static void setInstance(Context context) {
        CommonUtil.context = context;
    }

    public static boolean isServerRunning(Class server){
        if(server == null){
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // am.getRunningServiceControlPanel()
        return false;
    }

    public static boolean isTopApp(String packName) {
        if (packName == null || "".equals(packName)) {
            return false;
        }
        String tpn = getTopPackageName();
        return !(tpn == null || "".equals(tpn)) && packName.equals(tpn);
    }

    public static String getTopPackageName() {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return null;
        }
        List<RunningTaskInfo> tis = am.getRunningTasks(1);
        if (tis.isEmpty()) {
            return null;
        }
        return tis.get(0).topActivity.getPackageName();
    }

    /**
     * 判断网络是否可用
     *
     * @param context context
     * @return true 可用,false 不可用
     */
    public static boolean isNetAvailable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo[] nis = cm.getAllNetworkInfo();
        for (NetworkInfo ni : nis) {
            if (ni.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }
}
