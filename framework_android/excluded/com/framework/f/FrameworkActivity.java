package org.smarty.core.f;

/**
 * Created by kyokuryou on 15-3-12.
 */
public abstract class FrameworkActivity extends ActivityBean {

    @Override
    protected final void initActivityBean() throws RuntimeException {
        initFrameworkActivity();
    }

    protected abstract void initFrameworkActivity() throws RuntimeException;
}
