package org.smarty.core.beans;

import org.smarty.core.R;

/**
 * 常量定义
 */
public interface Constant {
    String DIALOG_ICON_KEY = "dialog-icon";
    String DIALOG_TITLE_KEY = "dialog-title";
    String DIALOG_POSITIVE_KEY = "dialog-positive";
    String DIALOG_NEGATIVE_KEY = "dialog-negative";
    String DIALOG_NEUTRAL_KEY = "dialog-neutral";

    int DIALOG_DEFAULT_ICON = android.R.drawable.ic_dialog_alert;
    int DIALOG_DEFAULT_TITLE = R.string.dialog_info_title;
    int DIALOG_DEFAULT_POSITIVE = R.string.dialog_ok_button;
    int DIALOG_DEFAULT_NEGATIVE = R.string.dialog_cancel_button;
    int DIALOG_DEFAULT_NEUTRAL = R.string.dialog_ignore_button;

    int DIALOG_PROGRESS_ICON = android.R.drawable.ic_dialog_info;
    int DIALOG_PROGRESS_TITLE = R.string.dialog_progress_title;
    int DIALOG_PROGRESS_LOADING = R.string.dialog_progress_loading;

    int DEFAULT_MENU_LAYOUT = android.R.layout.simple_list_item_1;
    int DEFAULT_LIST_LAYOUT = com.android.internal.R.id.list;
    int DEFAULT_LIST_CONTENT = com.android.internal.R.layout.list_content;
    int DEFAULT_EMPTY_LAYOUT = com.android.internal.R.id.empty;

    String DB_DEFAULT_PATH = "data/data/com.vc.gzlt/files/";
}
