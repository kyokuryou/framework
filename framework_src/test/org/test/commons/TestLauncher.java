package org.test.commons;

import java.util.Set;
import org.smarty.core.launcher.AbsLauncher;

/**
 * Created by kyokuryou on 15-4-1.
 */
public class TestLauncher extends AbsLauncher {
    protected Set<ClassLoader> getLauncher() {
        Set<ClassLoader> cls = super.getLauncher();
        cls.add(TestLauncher.class.getClassLoader());
        return cls;
    }
}
