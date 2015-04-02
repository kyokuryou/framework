package org.test.commons;

import org.smarty.web.commons.WebLauncher;

import java.util.Set;

/**
 * Created by kyokuryou on 15-4-2.
 */
public class TestWebLauncher extends WebLauncher {
    @Override
    protected Set<ClassLoader> getLauncher() {
        Set<ClassLoader> cls =  super.getLauncher();
        cls.add(TestWebLauncher.class.getClassLoader());
        return cls;
    }
}
