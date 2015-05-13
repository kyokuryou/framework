package org.smarty.core.listener;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnShowListener;
import android.view.KeyEvent;

/**
 * AbsDialogListener
 */
public abstract class AbsDialogListener implements OnShowListener, OnDismissListener, OnKeyListener, OnCancelListener {

    @Override
    public void onDismiss(DialogInterface dialog) {

    }

    @Override
    public void onShow(DialogInterface dialog) {

    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }

    public void onKeyDown(DialogInterface dialog, int keyCode) {

    }

    @Override
    public final boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (dialog == null || !((Dialog) dialog).isShowing()) {
            return false;
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                dialog.dismiss();
                break;
            case KeyEvent.KEYCODE_HOME:
                break;
            case KeyEvent.KEYCODE_MENU:
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                break;
            case KeyEvent.KEYCODE_POWER:
                break;
            default:
                dialog.dismiss();
        }
        onKeyDown(dialog, keyCode);
        return true;
    }
}
