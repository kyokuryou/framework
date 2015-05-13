package org.smarty.core.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import org.smarty.core.base.IController;

abstract class BasicActivity extends Activity {
    protected final String TAG = getClass().getSimpleName();
    //private final ControllerHandler controllerHandler = new ControllerHandler();
    //private IController controller;

    protected void setViewInit() {

    }

    protected void bindListener() {

    }

    public IController getController() {
        //return controller;
        return null;
    }

    protected abstract void setContentView();

    @Override
    protected void onCreate(Bundle bundle) {
        Log.i(TAG, "onCreate");
        //initController();
        super.onCreate(bundle);
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart");
        //initController();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        // initController();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //controller.destroy();
    }

    protected void initController() {
        //Class cls = BasicActivity.this.getClass();
        //Proxy proxy = (Proxy) cls.getAnnotation(Proxy.class);
        //Class target = proxy.value();
        //if (!IController.class.isAssignableFrom(target)) {
        //    throw new RuntimeException();
        //}
        //controller = (IController) controllerHandler.bind(proxy.value());
        //controller.afterPropertiesSet(BasicActivity.this);
    }


}
