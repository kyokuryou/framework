package org.smarty.core.dialog;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import org.smarty.core.beans.Constant;
import org.smarty.core.beans.DialogParams;
import org.smarty.core.utils.DialogUtil;

/**
 * 自定义Dialog
 */
public final class CustomDialog {
    protected final String TAG = "CustomDialog";
    private Context context;
    private Dialog dialog;
    private DialogParams dialogParams;

    public CustomDialog(Context context, DialogParams dialogParams) {
        Log.i(TAG, "new a dialog panel");
        this.context = context;
        this.dialogParams = dialogParams;
        this.dialog = onCreateDialog();
    }

    public void showDialog() {
        Log.i(TAG, "show a dialog panel");
        if (dialog == null || dialog.isShowing()) {
            Log.e(TAG, "dialog is showing");
            throw new RuntimeException("dialog is showing");
        }
        OnShowListener shol = dialogParams.getShowListener();
        OnDismissListener disl = dialogParams.getDismissListener();
        if (shol != null) {
            dialog.setOnShowListener(shol);
        }
        if (disl != null) {
            dialog.setOnDismissListener(disl);
        }
        dialog.show();
    }

    public void dismissDialog() {
        Log.i(TAG, "dismiss a dialog panel");
        if (dialog == null || !dialog.isShowing()) {
            Log.e(TAG, "dialog is not showing");
            throw new RuntimeException("dialog is not showing");
        }
        dialog.dismiss();
    }

    protected Dialog onCreateDialog() {
        Log.i(TAG, "create a dialog panel");
        if (dialogParams == null) {
            Log.e(TAG, "dialog params is must!");
            return null;
        }
        Bundle args = dialogParams.getArguments();
        if (args == null || args.isEmpty()) {
            args = DialogUtil.getTextArguments();
        }
        Builder builder = new Builder(context);
        builder.setCancelable(false);
        // 顶部
        createTop(builder, args);
        // 中部
        createCenter(builder);
        // 底部
        createBottom(builder, args);
        // 绑定监听器
        bindListener(builder);
        return builder.create();
    }

    private void createTop(Builder builder, Bundle args) {
        Log.i(TAG, "create dialog top layout");
        builder.setIcon(args.getInt(Constant.DIALOG_ICON_KEY));
        builder.setTitle(args.getInt(Constant.DIALOG_TITLE_KEY));
    }

    private void createCenter(Builder builder) {
        Log.i(TAG, "create dialog context layout");
        String text = dialogParams.getText();
        CharSequence[] items = dialogParams.getItems();
        View view = dialogParams.getView();

        if (text != null && !"".equals(text)) {
            builder.setMessage(text);
        } else if (items != null && items.length != 0) {
            createItems(builder);
        } else if (view != null) {
            builder.setView(view);
        }
    }

    private void createItems(Builder builder) {
        Log.i(TAG, "create dialog list items layout");
        DialogParams.ItemType it = dialogParams.getItemsType();
        CharSequence[] items = dialogParams.getItems();
        if (DialogParams.ItemType.LIST == it) {
            builder.setItems(items, dialogParams.getItemsListener());
        } else if (DialogParams.ItemType.MULTI == it) {
            boolean[] ci = new boolean[items.length];
            builder.setMultiChoiceItems(items, ci, dialogParams.getMultiChoiceClickListener());
        } else if (DialogParams.ItemType.SINGLE == it) {
            builder.setSingleChoiceItems(items, 0, dialogParams.getItemsListener());
        }
    }

    private void createBottom(Builder builder, Bundle args) {
        Log.i(TAG, "create dialog bottom layout");
        // 确定 按钮click事件
        OnClickListener pos = dialogParams.getPositiveListener();
        // 取消 按钮click事件
        OnClickListener neg = dialogParams.getNegativeListener();
        // 忽略 按钮click事件
        OnClickListener neu = dialogParams.getNeutralListener();
        if (pos != null) {
            builder.setPositiveButton(args.getInt(Constant.DIALOG_POSITIVE_KEY), pos);
        }
        if (neg != null) {
            builder.setNegativeButton(args.getInt(Constant.DIALOG_NEGATIVE_KEY), neg);
        }
        if (neu != null) {
            builder.setNeutralButton(args.getInt(Constant.DIALOG_NEUTRAL_KEY), neu);
        }
    }

    private void bindListener(Builder builder) {
        Log.i(TAG, "bind dialog cancel/key event");
        OnCancelListener canl = dialogParams.getCancelListener();
        OnKeyListener keyl = dialogParams.getKeyListener();
        if (canl != null) {
            builder.setOnCancelListener(canl);
        }
        if (keyl != null) {
            builder.setOnKeyListener(keyl);
        }
    }
}
