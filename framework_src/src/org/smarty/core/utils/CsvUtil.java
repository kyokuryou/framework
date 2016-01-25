package org.smarty.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.smarty.core.bean.Csv;
import org.smarty.core.common.BaseConstant;
import org.smarty.core.support.csvreader.CsvReader;
import org.smarty.core.support.csvreader.CsvWriter;

/**
 * CSV工具
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class CsvUtil {
    private static Log logger = LogFactory.getLog(CsvUtil.class);

    /**
     * 由fileName描述的文件字符串,读取CSV;
     *
     * @param fileName 文件名
     * @param csv      csv
     * @return csv
     * @throws IOException
     */
    public static Csv readCsv(String fileName, Csv csv) throws IOException {
        InputStream is = new FileInputStream(fileName);
        try {
            return readCsv(is, csv);
        } finally {
            is.close();
        }
    }

    /**
     * 由File对象,读取CSV文件
     *
     * @param file File
     * @param csv  csv
     * @return csv
     * @throws IOException
     */
    public static Csv readCsv(File file, Csv csv) throws IOException {
        InputStream is = new FileInputStream(file);
        try {
            return readCsv(new FileInputStream(file), csv);
        } finally {
            is.close();
        }
    }

    /**
     * 从流中读取Csv文件
     *
     * @param is  输入流
     * @param csv csv
     * @return csv
     * @throws IOException
     */
    public static Csv readCsv(InputStream is, Csv csv) throws IOException {
        CsvReader csvr = new CsvReader(is, BaseConstant.LETTERS_COMMA, Charset.forName(csv.getCharset()));
        try {
            // 读取表头数据
            if (csvr.readHeaders()) {
                csv.setHeaders(csvr.getHeaders());
            }
            List<Object[]> csvList = new LinkedList<Object[]>();
            //逐行读入除表头数据
            while (csvr.readRecord()) {
                csvList.add(csvr.getValues());
            }
            csv.setValues(csvList);
        } catch (IOException e) {
            logger.warn(e);
        } finally {
            csvr.close();
        }
        return csv;
    }


    /**
     * 写入CSV;使用ServletOutputStream时,不生成实体文件.
     *
     * @param os  输出流
     * @param csv csv
     */
    public void writeCsv(OutputStream os, Csv csv) {
        CsvWriter csvw = new CsvWriter(os, BaseConstant.LETTERS_COMMA, Charset.forName(csv.getCharset()));
        try {
            csvw.writeRecord(csv.getHeaders());
            for (Object[] obj : csv.getValues()) {
                // taskExecutor.execute(new WriteTask(csvw, obj));
            }
        } catch (IOException e) {
            logger.warn(e);
        } finally {
            csvw.close();
        }

    }
}
