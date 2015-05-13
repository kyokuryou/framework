package org.smarty.core.test.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.smarty.core.app.AbsAsyncListActivity;
import org.smarty.core.test.controller.SmsController;

/**
 * Created Date 2015/04/16
 *
 * @author kyokuryou
 * @version 1.0
 */
public class SmsActivity extends AbsAsyncListActivity {
    private SmsController sc = new SmsController(this);

    @Override
    protected void setContentView() {
        super.setContentView();
    }

    @Override
    protected void setViewInit() {
        setListViewAdapter(sc.getAdapter());
    }

    @Override
    protected void bindItemListener(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    @Override
    protected int getListLayout() {
        return super.getListLayout();
    }
}
