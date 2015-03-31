package org.smarty.core.utils;

import org.smarty.core.bean.ExcelUnit;
import org.smarty.core.exception.OfficeException;
import org.smarty.core.test.AbsTestCase;
import org.smarty.core.utils.ExcelUtil.ReadExcel;
import org.smarty.core.utils.ExcelUtil.WriteExcel;
import org.junit.Test;

import java.util.List;

/**
 *
 */
public class ExcelUtilTest extends AbsTestCase {
    @Test
    public void testReadExcel() throws OfficeException {
        try {
            ReadExcel re = ExcelUtil.instanceRead("E:/test.xls",20);

            System.out.println(re.getSheetName(0));
            System.out.println(re.getSheetNames());

            List<ExcelUnit> eus1 = re.getCellExcelUnit("Sheet1", 0);
            List<ExcelUnit> eus2 = re.getRowExcelUnit("Sheet1", 0);
            ExcelUnit eus3 = re.getExcelUnit("Sheet1", 0, 0);
            Integer row = re.getRows("Sheet1");

            System.out.println("--------------------");
        } catch (OfficeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWriteExcelA() throws OfficeException {
        WriteExcel we = ExcelUtil.instanceWrite(ExcelUtil.VERSION07);
        we.setExcelUnit(createExcelUnit());
    }

    private ExcelUnit createExcelUnit(){
        return null;
    }
    public void testWriteExcel() throws OfficeException {
//        WriteExcel we = ExcelUtil.instanceWrite(ExcelUtil.VERSION03);
//        ExcelUnit eu = we.createExcelUnit("yyyy-MM-dd HH:mm:ss");
//        eu.setSheetName("中文");
//        eu.setRowNum(0);
//        eu.setRowMergedCount(2);
//        eu.setCellNum(0);
//        eu.setValue(new Date());
//
//
//        ExcelUnit eu1 = we.createExcelUnit();
//        eu1.setSheetName("abc");
//        eu1.setRowNum(0);
//        eu1.setCellNum(1);
//        eu1.setValue("你好");
//        we.setExcelUnit(eu,eu1);
//        try {
//            FileOutputStream fileOut = new FileOutputStream("E:/test1.xls");
//            we.outStream(fileOut);
//            fileOut.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
