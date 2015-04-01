package org.test.commons;

import org.smarty.core.launcher.AbsLauncher;

import java.util.Set;

/**
 * Created by kyokuryou on 15-4-1.
 */
public class TestLauncher  extends AbsLauncher{
    @Override
    protected Set<ClassLoader> getLauncher() {
        Set<ClassLoader> cls =  super.getLauncher();
        cls.add(TestLauncher.class.getClassLoader());
        return cls;
    }
}
