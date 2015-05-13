package com.framework.core;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class IndexActivity extends Activity{
    public IndexActivity() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.attach(new TestFragment());
        tx.commit();
    }
}
