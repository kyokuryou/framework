package org.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created with Liang Qu
 * Create User: Liang Qu
 * Update User: Liang Qu
 * Create Date: 2013/12/04
 * Update Date: 2013/12/04
 */
public class DocInputStream extends FileInputStream {

    public DocInputStream(File file) throws FileNotFoundException {
        super(file);
    }

    public String xmlRead() throws IOException {
        byte[] bytes = new byte[available()];
        if (read(bytes) == -1) {
            return null;
        }
        return new String(bytes);
    }
}
