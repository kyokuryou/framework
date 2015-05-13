package org.smarty.core.test.activity;

import android.widget.Button;
import org.smarty.core.R;
import org.smarty.core.app.AbsSimpleActivity;
import org.smarty.core.handler.Proxy;
import org.smarty.core.test.controller.TestSimpleController;

/**
 * Created by kyokuryou on 15-3-11.
 */
@Proxy(TestSimpleController.class)
public class TestSimpleActivity extends AbsSimpleActivity {


    @Override
    protected void setContentView() {
        setContentView(R.layout.test);
    }

    @Override
    protected void setViewInit() {
        super.setViewInit();
    }

    @Override
    protected void bindListener() {
        TestSimpleController tsc = (TestSimpleController) getController();
        Button btn = (Button) findViewById(R.id.testBtn);
        btn.setOnClickListener(tsc.testBtn());
    }
}
