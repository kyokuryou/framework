package org.smarty.core.listener;

import android.content.DialogInterface;

/**
 * 单选按钮多事件实现
 */
public class SingleClickListener extends BasicClickListener {
    private int choice;

    @Override
    protected void whichClick(int which) {
        onItemClick(which, true);
    }

    @Override
    public void onItemClick(int choice, boolean checked) {
        this.choice = choice;
    }

    public void onPositiveClick(DialogInterface dialog, int choices) {

    }

    @Override
    public final void onPositiveClick(DialogInterface dialog) {
        onPositiveClick(dialog, choice);
    }
}
