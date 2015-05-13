package org.smarty.core.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import org.smarty.core.beans.Constant;
import org.smarty.core.beans.DialogParams;
import org.smarty.core.dialog.CustomDialog;
import org.smarty.core.listener.ListClickListener;
import org.smarty.core.listener.MultiClickListener;
import org.smarty.core.listener.ProgressListener;
import org.smarty.core.listener.SingleClickListener;
import org.smarty.core.listener.TextClickListener;

/**
 * dialog
 */
public final class DialogUtil {
    public static CustomDialog createProgressDialog(Context context, int textId, ProgressListener listener) {
        return createProgressDialog(context, null, textId, listener);
    }

    public static CustomDialog createTextDialog(Context context, String text, TextClickListener listener) {
        return createTextDialog(context, null, text, listener);
    }

    public static CustomDialog createMultiItemDialog(Context context, CharSequence[] items, MultiClickListener listener) {
        return createMultiItemDialog(context, null, items, listener);
    }

    public static CustomDialog createSingleItemDialog(Context context, CharSequence[] items, SingleClickListener listener) {
        return createSingleItemDialog(context, null, items, listener);
    }

    public static CustomDialog createListItemDialog(Context context, CharSequence[] items, ListClickListener listener) {
        return createListItemDialog(context, null, items, listener);
    }

    public static CustomDialog createProgressDialog(Context context, Bundle bundle, int textId, ProgressListener listener) {
        DialogParams dp = new DialogParams();
        if (bundle == null || bundle.isEmpty()) {
            dp.setArguments(getProgressArguments());
        }
        dp.setArguments(bundle);
        dp.setView(createDefaultProgress(context, textId));
        dp.setShowListener(listener);
        dp.setDismissListener(listener);
        return new CustomDialog(context, dp);
    }

    public static CustomDialog createTextDialog(Context context, Bundle bundle, String text, TextClickListener listener) {
        DialogParams dp = new DialogParams();
        if (bundle == null || bundle.isEmpty()) {
            dp.setArguments(getTextArguments());
        }
        dp.setArguments(bundle);
        dp.setText(text);
        dp.setPositiveListener(listener);
        dp.setNegativeListener(listener);
        dp.setShowListener(listener);
        dp.setDismissListener(listener);
        return new CustomDialog(context, dp);
    }

    public static CustomDialog createMultiItemDialog(Context context, Bundle bundle, CharSequence[] items, MultiClickListener listener) {
        DialogParams dp = new DialogParams();
        if (bundle == null || bundle.isEmpty()) {
            dp.setArguments(getTextArguments());
        }
        dp.setArguments(bundle);
        dp.setItems(items);
        dp.setItemsType(DialogParams.ItemType.MULTI);
        dp.setMultiChoiceClickListener(listener);
        dp.setPositiveListener(listener);
        dp.setNegativeListener(listener);
        dp.setShowListener(listener);
        dp.setDismissListener(listener);
        return new CustomDialog(context, dp);
    }

    public static CustomDialog createSingleItemDialog(Context context, Bundle bundle, CharSequence[] items, SingleClickListener listener) {
        DialogParams dp = new DialogParams();
        if (bundle == null || bundle.isEmpty()) {
            dp.setArguments(getTextArguments());
        }
        dp.setArguments(bundle);
        dp.setItems(items);
        dp.setItemsType(DialogParams.ItemType.SINGLE);
        dp.setItemsListener(listener);
        dp.setPositiveListener(listener);
        dp.setNegativeListener(listener);
        dp.setShowListener(listener);
        dp.setDismissListener(listener);
        return new CustomDialog(context, dp);
    }

    public static CustomDialog createListItemDialog(Context context, Bundle bundle, CharSequence[] items, ListClickListener listener) {
        DialogParams dp = new DialogParams();
        if (bundle == null || bundle.isEmpty()) {
            dp.setArguments(getTextArguments());
        }
        dp.setArguments(bundle);
        dp.setItems(items);
        dp.setItemsType(DialogParams.ItemType.LIST);
        dp.setItemsListener(listener);
        dp.setShowListener(listener);
        dp.setDismissListener(listener);
        return new CustomDialog(context, dp);
    }

    public static View createDefaultProgress(Context context, int textId) {
        LayoutInflater li = LayoutInflater.from(context);
        TypedArray ta = context.obtainStyledAttributes(
                null,
                com.android.internal.R.styleable.AlertDialog,
                com.android.internal.R.attr.alertDialogStyle,
                0
        );
        View view = li.inflate(
                ta.getResourceId(
                        com.android.internal.R.styleable.AlertDialog_progressLayout,
                        com.android.internal.R.layout.progress_dialog
                ),
                null
        );
        TextView tv = (TextView) view.findViewById(android.R.id.message);
        tv.setText(textId);
        return view;
    }

    public static Bundle getTextArguments() {
        Bundle args = new Bundle();
        // 图标
        args.putInt(Constant.DIALOG_ICON_KEY, Constant.DIALOG_DEFAULT_ICON);
        // 标题
        args.putInt(Constant.DIALOG_TITLE_KEY, Constant.DIALOG_DEFAULT_TITLE);
        // 确定 按钮默认值
        args.putInt(Constant.DIALOG_POSITIVE_KEY, Constant.DIALOG_DEFAULT_POSITIVE);
        // 取消 按钮默认值
        args.putInt(Constant.DIALOG_NEGATIVE_KEY, Constant.DIALOG_DEFAULT_NEGATIVE);
        // 忽略 按钮默认值
        args.putInt(Constant.DIALOG_NEUTRAL_KEY, Constant.DIALOG_DEFAULT_NEUTRAL);
        return args;
    }

    public static Bundle getProgressArguments() {
        Bundle args = new Bundle();
        // 图标
        args.putInt(Constant.DIALOG_ICON_KEY, Constant.DIALOG_PROGRESS_ICON);
        // 标题
        args.putInt(Constant.DIALOG_TITLE_KEY, Constant.DIALOG_PROGRESS_TITLE);
        return args;
    }

    public static OnClickListener getDefaultClickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        };
    }
}
