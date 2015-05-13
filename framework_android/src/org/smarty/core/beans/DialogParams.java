package org.smarty.core.beans;

import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.view.View;

/**
 * dialog参数
 */
public class DialogParams {
    public enum ItemType {
        LIST, MULTI, SINGLE
    }

    private boolean isProgress;

    // 基础属性
    private Bundle arguments;
    // 列表类型
    private ItemType itemsType = ItemType.LIST;
    // 内容(文本)
    private String text;
    // 内容(列表)
    private CharSequence[] items;
    // 内容(View)
    private View view;
    // 内容列表点击事件
    private OnClickListener itemsListener;
    // 确定 按钮click事件
    private OnClickListener positiveListener;
    // 取消 按钮click事件
    private OnClickListener negativeListener;
    // 忽略 按钮click事件
    private OnClickListener neutralListener;
    // 其他事件绑定
    private OnShowListener showListener;
    private OnDismissListener dismissListener;
    private OnCancelListener cancelListener;
    private OnKeyListener keyListener;
    private OnMultiChoiceClickListener multiChoiceClickListener;

    public DialogParams() {
    }

    public boolean isProgress() {
        return isProgress;
    }

    public void setProgress(boolean isProgress) {
        this.isProgress = isProgress;
    }

    public Bundle getArguments() {
        return arguments;
    }

    public void setArguments(Bundle arguments) {
        this.arguments = arguments;
    }

    public ItemType getItemsType() {
        return itemsType;
    }

    public void setItemsType(ItemType itemsType) {
        this.itemsType = itemsType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CharSequence[] getItems() {
        return items;
    }

    public void setItems(CharSequence[] items) {
        this.items = items;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public OnClickListener getItemsListener() {
        return itemsListener;
    }

    public void setItemsListener(OnClickListener itemsListener) {
        this.itemsListener = itemsListener;
    }

    public OnClickListener getPositiveListener() {
        return positiveListener;
    }

    public void setPositiveListener(OnClickListener positiveListener) {
        this.positiveListener = positiveListener;
    }

    public OnClickListener getNegativeListener() {
        return negativeListener;
    }

    public void setNegativeListener(OnClickListener negativeListener) {
        this.negativeListener = negativeListener;
    }

    public OnClickListener getNeutralListener() {
        return neutralListener;
    }

    public void setNeutralListener(OnClickListener neutralListener) {
        this.neutralListener = neutralListener;
    }

    public OnShowListener getShowListener() {
        return showListener;
    }

    public void setShowListener(OnShowListener showListener) {
        this.showListener = showListener;
    }

    public OnDismissListener getDismissListener() {
        return dismissListener;
    }

    public void setDismissListener(OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    public OnCancelListener getCancelListener() {
        return cancelListener;
    }

    public void setCancelListener(OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public OnKeyListener getKeyListener() {
        return keyListener;
    }

    public void setKeyListener(OnKeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public OnMultiChoiceClickListener getMultiChoiceClickListener() {
        return multiChoiceClickListener;
    }

    public void setMultiChoiceClickListener(OnMultiChoiceClickListener multiChoiceClickListener) {
        this.multiChoiceClickListener = multiChoiceClickListener;
    }
}
