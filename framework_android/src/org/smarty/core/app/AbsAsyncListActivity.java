package org.smarty.core.app;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import org.smarty.core.beans.Constant;
import org.smarty.core.dialog.CustomDialog;
import org.smarty.core.widget.ListViewAdapter;
import org.smarty.core.listener.ProgressListener;
import org.smarty.core.utils.DialogUtil;

/**
 * 异步ListView抽象实现
 */
public abstract class AbsAsyncListActivity extends BasicActivity implements AsyncActivity {
    private ListView listView;
    private ListViewAdapter adapter;
    private CustomDialog progressDialog;
    private Handler mHandler = new Handler();
    private boolean mFinishedStart = false;
    private Runnable asyncListRunnable = new Runnable() {
        @Override
        public void run() {
            listView.focusableViewAvailable(listView);
        }
    };

    public final void setListViewAdapter(ListViewAdapter adapter) {
        synchronized (this) {
            if (listView != null) {
                return;
            }
            setContentView();
            this.adapter = adapter;
            listView.setAdapter(adapter);
        }
    }

    protected int getListLayout() {
        return Constant.DEFAULT_LIST_LAYOUT;
    }


    protected void bindItemListener(ListView listView) {

    }

    @Override
    protected void bindListener() {
        bindItemListener(listView);
    }

    @Override
    protected void setContentView() {
        if (listView != null) {
            return;
        }
        setContentView(Constant.DEFAULT_LIST_CONTENT);
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewInit();
    }

    @Override
    protected final void onRestoreInstanceState(Bundle savedInstanceState) {
        setContentView();
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public final void onContentChanged() {
        super.onContentChanged();
        int lvl = getListLayout();
        View emptyView = findViewById(Constant.DEFAULT_EMPTY_LAYOUT);
        listView = (ListView) findViewById(lvl);
        if (listView == null) {
            throw new RuntimeException("ListView is null");
        }
        if (emptyView != null) {
            listView.setEmptyView(emptyView);
        }
        bindListener();
        if (mFinishedStart) {
            setListViewAdapter(adapter);
        }
        mHandler.post(asyncListRunnable);
        mFinishedStart = true;
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = DialogUtil.createProgressDialog(this, Constant.DIALOG_PROGRESS_LOADING, new ProgressListener());
        }
        progressDialog.showDialog();
    }

    @Override
    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismissDialog();
        }
    }
}

