package org.smarty.core.utils;

import android.content.Context;
import android.content.res.Resources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public final class FileUtil {

    public static String readFile(Context context, int fileName) {
        Resources res = context.getResources();
        InputStream in = res.openRawResource(fileName);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            int len = 0;
            byte[] bytes = new byte[in.available()];
            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }
}
