package org.smarty.core.app;

import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import org.smarty.core.R;
import org.smarty.core.beans.Constant;

/**
 * WebView抽象实现,需配合AbsWebApplication使用
 */
public abstract class AbsWebViewActivity extends BasicActivity {
    private WebView webView;

    @Override
    protected final void setContentView() {
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);
        webView = new WebView(this);
        setContentView(webView);
    }

    @Override
    public AbsWebApplication getApplicationContext() {
        return (AbsWebApplication) super.getApplicationContext();
    }

    protected WebView getWebView() {
        return webView;
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        // ThemesUtil.currentTheme(this);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                setTitle(Constant.DIALOG_PROGRESS_TITLE);
                setProgress(progress * 100);
                if (progress == 100) {
                    setTitle(R.string.app_name);
                }
            }
        });
        bindListener();
    }
}
