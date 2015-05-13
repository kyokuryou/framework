package org.smarty.core.commons;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import org.smarty.core.beans.App;
import org.smarty.core.utils.AppUtil;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AppManager {
    private Resources resources;
    private PackageManager packageManager;

    public AppManager(Context context) {
        Assert.notNull(context, "context must not be null");
        resources = context.getResources();
        packageManager = context.getPackageManager();
    }

    public App refApp(String name) {
        if ("alarm".equals(name)) {
            return getAlarm();
        } else if ("camera".equals(name)) {
            return getCamera();
        } else if ("photo".equals(name)) {
            return getPhoto();
        } else if ("Dial".equals(name)) {
            return getDial();
        } else if ("sms".equals(name)) {
            return getSms();
        } else if ("contacts".equals(name)) {
            return getContacts();
        }
        return null;
    }

    public List<App> getDefaultApps() {
        List<App> apps = new ArrayList<App>();
//        String[] as = resources.getStringArray(Constant.DEFAULT_APPS);
//        for (String a : as) {
//            apps.add(AppUtil.createApp(a));
//        }
        return apps;
    }

    public List<App> getLocalApps() {
        List<App> apps = new ArrayList<App>();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> ris = packageManager.queryIntentActivities(intent, 0);
        for (ResolveInfo ri : ris) {
            App app = AppUtil.createApp(packageManager, ri.activityInfo, null);
            setAppIcon(app, ri.activityInfo);
            apps.add(app);
        }
        return apps;
    }

    /**
     * 闹钟应用
     *
     * @return
     */
    public App getAlarm() {
        Intent intent = new Intent();
        intent.setAction(AlarmClock.ACTION_SET_ALARM);
        return getResolveApp(intent);
    }

    /**
     * 相机应用
     *
     * @return
     */
    public App getCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        return getResolveApp(intent);
    }

    /**
     * 相册应用
     *
     * @return
     */
    public App getPhoto() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return getResolveApp(intent);
    }

    /**
     * 拨号应用
     *
     * @return
     */
    public App getDial() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        return getResolveApp(intent);
    }

    /**
     * 短信应用
     *
     * @return
     */
    public App getSms() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setAction(Intent.ACTION_MAIN);
        intent.setData(Uri.parse("content://mms-sms/"));
        return getResolveApp(intent);
    }

    /**
     * 联系人应用
     *
     * @return
     */
    public App getContacts() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(ContactsContract.Contacts.CONTENT_URI);
        return getResolveApp(intent);
    }

    private App getResolveApp(Intent intent) {
        ResolveInfo ri = packageManager.resolveActivity(intent, 0);
        if (ri == null) {
            return null;
        }
        return AppUtil.createApp(packageManager, ri.activityInfo, intent);
    }


    private void setAppIcon(App app, ActivityInfo ai) {
        Drawable drawable = ai.loadIcon(packageManager);
        app.setIcon(AppUtil.convertByte(drawable));
    }
}
