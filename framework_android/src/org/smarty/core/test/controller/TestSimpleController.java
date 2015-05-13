package org.smarty.core.test.controller;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import org.smarty.core.base.AbsController;

/**
 * Created by kyokuryou on 15-3-11.
 */
public class TestSimpleController extends AbsController {

    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet");
    }


    public OnClickListener testBtn() {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                btn.setText("点击");
            }
        };
    }
}
