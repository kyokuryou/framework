package org.smarty.core.test.controller;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import org.smarty.core.R;
import org.smarty.core.base.AbsController;
import org.smarty.core.widget.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created Date 2015/04/16
 *
 * @author kyokuryou
 * @version 1.0
 */
public class SmsController extends AbsController {

    private Context context;

    public SmsController(Context context) {
        this.context = context;
    }

    public ListViewAdapter getAdapter() {
        final List<String> data = getData();
        return new ListViewAdapter(context) {
            @Override
            public int getLayoutId() {
                return R.layout.test_async_list_item;
            }

            @Override
            public View getConvertView(int i, View cv) {
                TextView id = (TextView) cv.findViewById(R.id.synd_id);
                id.setText(String.valueOf(i));
                TextView name = (TextView) cv.findViewById(R.id.synd_name);
                name.setText(data.get(i));
                return cv;
            }

            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public Object getItem(int position) {
                return data.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }
        };
    }

    private List<String> getData() {
        List<String> data = new ArrayList<String>();
        for (int i = 1; i < 20; i++) {
            data.add("test" + i);
        }
        return data;
    }
}
