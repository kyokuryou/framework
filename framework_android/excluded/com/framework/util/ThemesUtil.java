package org.smarty.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.vc.VcConstant;
import org.smarty.core.beans.Constant;
import org.springframework.util.Assert;

/**
 * 获取主题
 */
public final class ThemesUtil {

    public static void changeTheme(Activity activity, int theme) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.setTheme(theme);
        // 更新
        updateTheme(activity, theme);
    }

    public static void updateTheme(Activity activity, int theme) {
        Assert.notNull(activity, "'activity' must not be null");
        SharedPreferences th = activity.getSharedPreferences(Constant.SETTING_NAME, Context.MODE_PRIVATE);
        int ct = th.getInt("themes", Constant.DEFAULT_THEME);
        if (ct == theme) {
            return;
        }
        Editor se = th.edit();
        se.putInt("themes", theme);
        se.apply();
    }

    public static void currentTheme(Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        SharedPreferences th = activity.getSharedPreferences(Constant.SETTING_NAME, Context.MODE_PRIVATE);
        activity.setTheme(th.getInt("themes", Constant.DEFAULT_THEME));
    }
}
