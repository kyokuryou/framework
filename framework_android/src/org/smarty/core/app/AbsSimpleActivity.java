package org.smarty.core.app;

import android.os.Bundle;

/**
 * 通用简单Activity
 */
public abstract class AbsSimpleActivity extends BasicActivity {
    @Override
    protected final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView();
        setViewInit();
        bindListener();
        // ThemesUtil.currentTheme(this);
    }
}
