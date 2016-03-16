package org.test.commons;

import java.util.Set;
import org.smarty.web.commons.WebLauncher;

/**
 * Created by kyokuryou on 15-4-2.
 */
public class TestWebLauncher extends WebLauncher {

    protected Set<ClassLoader> getLauncher() {
        Set<ClassLoader> cls = super.getLauncher();
        cls.add(TestWebLauncher.class.getClassLoader());
        return cls;
    }
}
