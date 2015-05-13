package org.smarty.core.utils;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import org.smarty.core.R;
import org.smarty.core.beans.App;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public final class AppUtil {
    private final static String APP_JSON_EXP = "[\\w[^\\[\\],{}]]+";

    public static App createApp(String str) {
        Pattern srp = Pattern.compile(APP_JSON_EXP);
        Matcher srpm = srp.matcher(str);
        App app = new App();
        while (srpm.find()) {
            String[] ps = srpm.group().split(":");
            if (ps.length < 2) {
                continue;
            }
            try {
                setApp(app, ps[0], ps[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return app;
    }

    public static App createApp(PackageManager pm, ActivityInfo ai, Intent intent) {
        App app = new App();
        app.setName(ai.loadLabel(pm).toString());
        app.setPackageName(ai.packageName);
        app.setClassName(ai.name);
        app.setIntent(intent);
        return app;
    }

    public static Object setApp(Object app, String name, Object value) throws Exception {
        Assert.notNull(app, "'app' must not be null");
        Class acls = app.getClass();
        Field f = acls.getDeclaredField(name);
        Method m = getAppMethod(acls, f);
        if (!"drawable".equals(name)) {
            m.invoke(app, convertType(f.getType(), value));
        } else {
            m.invoke(app, getStyleableId(String.valueOf(value)));
        }
        return app;
    }

    public static int getStyleableId(String name) throws Exception {
        Class rs = R.styleable.class;
        Field rsf = rs.getField(String.valueOf(name));
        return rsf.getInt(null);
    }

    /**
     * 图片转换
     *
     * @param icon 图片
     * @return 图片字节码
     */
    public static byte[] convertByte(Drawable icon) {
        Bitmap bmicon = ((BitmapDrawable) icon).getBitmap();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bmicon.compress(Bitmap.CompressFormat.PNG, 100, os);
        return os.toByteArray();
    }

    /**
     * 类型强制类型转换
     *
     * @param type  类型
     * @param value 值
     * @return value
     */
    public static Object convertType(Class type, Object value) {
        if (type == null) {
            return null;
        }
        return type.cast(value);
    }

    public static Method getAppMethod(Class cls, Field field) throws Exception {
        char[] ca = field.getName().toCharArray();
        ca[0] = Character.toUpperCase(ca[0]);
        return cls.getMethod("set" + new String(ca), field.getType());
    }
}
