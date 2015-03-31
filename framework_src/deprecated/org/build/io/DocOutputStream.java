package org.core.io;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created with Liang Qu
 * Create User: Liang Qu
 * Update User: Liang Qu
 * Create Date: 2013/12/04
 * Update Date: 2013/12/04
 */
public class DocOutputStream extends FileOutputStream {

    public DocOutputStream(String file) throws FileNotFoundException{
        this(new File(file));
    }
    public DocOutputStream(File file) throws FileNotFoundException {
        super(file);
        File pf = file.getParentFile();
        if (!pf.exists()) {
            pf.mkdirs();
        }
    }

    public void xmlWrite(String xml) throws IOException {
        write(xmlPrettyFormat(xml).getBytes());
    }

    public String xmlPrettyFormat(String input) {
        try {
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Source xmlInput = new StreamSource(new StringReader(input));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            // transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
