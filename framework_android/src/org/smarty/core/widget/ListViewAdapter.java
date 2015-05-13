package org.smarty.core.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 异步适配器
 */
public abstract class ListViewAdapter extends BaseAdapter {
    private final LayoutInflater layoutInflater;

    public ListViewAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public final View getView(int i, View cv, ViewGroup pv) {
        if (cv == null) {
            cv = layoutInflater.inflate(getLayoutId(), pv, false);
        }
        return getConvertView(i, cv);
    }

    public abstract int getLayoutId();

    public abstract View getConvertView(int i, View cv);

}
