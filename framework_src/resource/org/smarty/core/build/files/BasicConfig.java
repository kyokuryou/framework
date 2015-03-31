package org.smarty.core.build.files;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with Liang Qu
 * Create User: Liang Qu
 * Update User: Liang Qu
 * Create Date: 2013/12/13
 * Update Date: 2013/12/13
 */
public class BasicConfig extends FileConfig {
    @Override
    public void buildFile(Properties ps) throws IOException {
        // TODO
    }

    private void clearFile(Properties ps){
        // TODO
    }

    @Override
    protected File getFile(String fileName) throws IOException {
        throw outException("该方法basic不支持",new UnsupportedOperationException());
    }

    @Override
    public String getString() throws IOException {
        throw outException("该方法basic不支持",new UnsupportedOperationException());
    }
}
