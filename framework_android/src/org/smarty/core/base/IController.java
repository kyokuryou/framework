package org.smarty.core.base;

import android.content.Context;

/**
 * 控制器接口
 */
public interface IController {
    public void afterPropertiesSet(Context context);

    public Context getApplicationContext();

    public void destroy();

}
