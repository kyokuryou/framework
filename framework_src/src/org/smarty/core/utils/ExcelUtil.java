package org.smarty.core.utils;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.smarty.core.bean.ExcelUnit;
import org.smarty.core.exception.OfficeException;
import org.smarty.core.io.RuntimeLogger;

import java.io.*;
import java.util.*;

/**
 * Microsoft Excel工具
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class ExcelUtil {
    private static RuntimeLogger logger = new RuntimeLogger(ExcelUtil.class);

    // 2003版本
    public final static int VERSION03 = 0;
    // 2007版本
    public final static int VERSION07 = 1;

    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private final static String NUMBER_FORMAT = "#,##,###,####.##";
    private final static String MONEY_FORMAT = "#,##,###,####.00";

    private ExcelUtil() {
    }

    /**
     * 以File对象初始化Excel读取器
     *
     * @return Excel读取器
     */
    public static ReadExcel instanceRead(String fileName, int version) throws OfficeException {
        try {
            return instanceRead(new FileInputStream(fileName), version);
        } catch (FileNotFoundException e) {
            logger.out(e);
            throw new OfficeException(e);
        }
    }

    /**
     * 以File对象初始化Excel读取器
     *
     * @return Excel读取器
     */
    public static ReadExcel instanceRead(File file, int version) throws OfficeException {
        try {
            return instanceRead(new FileInputStream(file), version);
        } catch (FileNotFoundException e) {
            logger.out(e);
            throw new OfficeException(e);
        }
    }

    /**
     * 以File对象初始化Excel读取器
     *
     * @return Excel读取器
     */
    public static ReadExcel instanceRead(InputStream is, int version) throws OfficeException {
        try {
            return new ReadExcel(is, version);
        } catch (IOException e) {
            logger.out(e);
            throw new OfficeException(e);
        }
    }

    public static WriteExcel instanceWrite(int version) throws OfficeException {
        return new WriteExcel(version);
    }

    /**
     * 根据值类型获得值
     *
     * @param hc 列
     * @return 值
     */
    public static Object getValue(Cell hc) {
        switch (hc.getCellType()) {
            // 数值
            case Cell.CELL_TYPE_NUMERIC: {
                //读取日期格式
                if (HSSFDateUtil.isCellDateFormatted(hc)) {
                    return hc.getDateCellValue();
                }
                //读取数字
                return hc.getNumericCellValue();
            }
            case Cell.CELL_TYPE_STRING:
                //读取String
                return hc.getStringCellValue();
            case Cell.CELL_TYPE_FORMULA:
                //读取公式
                return hc.getCellFormula();
            case Cell.CELL_TYPE_BLANK:
                return "";
            case Cell.CELL_TYPE_BOOLEAN:
                //读取boolean
                return hc.getBooleanCellValue();
            case Cell.CELL_TYPE_ERROR:
                //读取error
                return hc.getErrorCellValue();
            default:
                return null;
        }
    }

    /**
     * 设置单元格值
     *
     * @param hc    列
     * @param value 值
     */
    public static void setValue(Cell hc, Object value) {
        if (value == null) return;
        if (value instanceof Number) {
            hc.setCellType(Cell.CELL_TYPE_NUMERIC);
            hc.setCellValue(((Number) value).doubleValue());
        } else if (value instanceof String) {
            hc.setCellType(Cell.CELL_TYPE_STRING);
            hc.setCellValue((String) value);
        } else if (value instanceof Date) {
            hc.setCellType(Cell.CELL_TYPE_NUMERIC);
            hc.setCellValue((Date) value);
        } else if (value instanceof Calendar) {
            hc.setCellType(Cell.CELL_TYPE_NUMERIC);
            hc.setCellValue((Calendar) value);
        } else if (value instanceof RichTextString) {
            hc.setCellType(Cell.CELL_TYPE_STRING);
            hc.setCellValue((RichTextString) value);
        }
    }


    /**
     * Excel读取器
     */
    public final static class ReadExcel {
        private InputStream is;
        private Workbook wb;

        private ReadExcel(InputStream is, int vs) throws IOException, OfficeException {
            if (is == null) return;
            this.is = is;
            try {
                if (vs == ExcelUtil.VERSION03) {
                    wb = new HSSFWorkbook(is);
                } else if (vs == ExcelUtil.VERSION07) {
                    wb = new XSSFWorkbook(is);
                } else {
                    throw new OfficeException("office version [" + vs + "] not find");
                }
            } catch (IOException e) {
                logger.out(e);
                throw e;
            }
        }

        /**
         * 获取所有Sheet名称
         *
         * @return Sheet名称集合
         */
        public List<String> getSheetNames() {
            List<String> sns = new ArrayList<String>();
            for (int i = 0, len = wb.getNumberOfSheets(); i < len; i++) {
                sns.add(wb.getSheetName(i));
            }
            return sns;
        }

        /**
         * 对应sheetIndex的Sheet名
         *
         * @param sheetIndex Sheet索引
         * @return Sheet名称
         */
        public String getSheetName(Integer sheetIndex) {
            return wb.getSheetName(sheetIndex);
        }

        /**
         * 获取sheet内的行数
         *
         * @param sheetName sheet名
         * @return 行数
         */
        public Integer getRows(String sheetName) {
            Sheet sh = wb.getSheet(sheetName);
            if (sh == null) {
                return null;
            }
            return sh.getPhysicalNumberOfRows();
        }

        /**
         * 获得一个单元格
         *
         * @param sheetName sheetName
         * @param row       行号
         * @param cell      列号
         * @return 单元格
         */
        public ExcelUnit getExcelUnit(String sheetName, Integer row, Integer cell) {
            Sheet sh = wb.getSheet(sheetName);
            if (sh == null) {
                return null;
            }
            Row hr = sh.getRow(row);
            // 取出单元格属性
            ExcelUnit eu = getExcelUnit(hr, cell);
            // 设置SheetName
            eu.setSheetName(sheetName);
            return eu;
        }


        /**
         * 获得一行
         *
         * @param sheetName sheetName
         * @param row       行号
         * @return 一行
         */
        public List<ExcelUnit> getRowExcelUnit(String sheetName, Integer row) {
            Sheet sh = wb.getSheet(sheetName);
            if (sh == null) {
                return null;
            }

            List<ExcelUnit> euList = new ArrayList<ExcelUnit>();
            Row hr = sh.getRow(row);

            // 获得列的迭代器
            Iterator<Cell> cells = hr.cellIterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                // 取出单元格属性
                ExcelUnit eu = getExcelUnit(hr, cell.getColumnIndex());
                // 设置SheetName
                eu.setSheetName(sheetName);
                euList.add(eu);

            }
            return euList;
        }

        /**
         * 获得一列
         *
         * @param sheetName sheetName
         * @param cell      列号
         * @return 一列
         */
        public List<ExcelUnit> getCellExcelUnit(String sheetName, Integer cell) {
            Sheet sh = wb.getSheet(sheetName);
            if (sh == null) {
                return null;
            }

            List<ExcelUnit> euList = new ArrayList<ExcelUnit>();

            // 获取行的迭代器
            Iterator<Row> rows = sh.rowIterator();
            while (rows.hasNext()) {
                Row row = rows.next();
                // 取出单元格属性
                ExcelUnit eu = getExcelUnit(row, cell);
                // 设置SheetName
                eu.setSheetName(sheetName);
                euList.add(eu);
            }
            return euList;

        }

        /**
         * 获得一个单元格
         *
         * @param hr   行
         * @param cell 列号
         * @return 单元格
         */
        public ExcelUnit getExcelUnit(Row hr, Integer cell) {
            Cell hc = hr.getCell(cell);
            // 初始化单元格
            ExcelUnit eu = new ExcelUnit();
            // 行号
            eu.setRowNum(hr.getRowNum());
            // 列号
            eu.setCellNum(hc.getColumnIndex());
            // 超链接
            eu.setHyperlink(hc.getHyperlink());
            // 注释
            eu.setComment(hc.getCellComment());
            // 列样式
            eu.setCellStyle(hc.getCellStyle());
            // 值
            eu.setValue(getValue(hc));
            return eu;
        }

        /**
         * 关闭数据流
         */
        public void close() {
            if (is == null) return;
            try {
                is.close();
            } catch (IOException e) {
                logger.out(e);
            }
        }
    }

    /**
     * Excel写入器
     */
    public final static class WriteExcel {
        private Workbook wb;

        private WriteExcel(int vs) throws OfficeException {
            if (vs == ExcelUtil.VERSION03) {
                wb = new HSSFWorkbook();
            } else if (vs == ExcelUtil.VERSION07) {
                wb = new XSSFWorkbook();
            } else {
                throw new OfficeException("office version [" + vs + "] not find");
            }
        }

        /**
         * 合并列
         *
         * @param sheet     sheet
         * @param excelUnit ExcelUnit
         */
        public void mergedCells(Sheet sheet, ExcelUnit excelUnit) {
            // 合并行数
            Integer rmc = excelUnit.getRowMergedCount();
            // 合并列数
            Integer cmc = excelUnit.getCellMergedCount();
            // 行数和列数同时<=0时,视为不合并
            if (rmc <= 0 && cmc <= 0) return;
            // 合并开始行号
            Integer firstRow = excelUnit.getRowNum();
            // 计算合并结束行号
            Integer lastRow = firstRow + rmc;
            // 合并开始列号
            Integer firstCol = excelUnit.getCellNum();
            // 计算合并结束列号
            Integer lastCol = firstCol + cmc;
            // 与游标同步
            lastRow = lastRow > firstRow ? lastRow - 1 : 0;
            lastCol = lastCol > firstCol ? lastCol - 1 : 0;
            // 创建合并对象
            CellRangeAddress cra = new CellRangeAddress(
                    firstRow, lastRow,
                    firstCol, lastCol
            );
            // 合并
            sheet.addMergedRegion(cra);
        }

        public Sheet createSheet(String sheetName) {
            Sheet sh = wb.getSheet(sheetName);
            if (sh == null) {
                sh = wb.createSheet(sheetName);
            }
            return sh;
        }

        public Row createRow(Sheet sheet, int rowNum) {
            Row hr = sheet.getRow(rowNum);
            if (hr == null) {
                // 创建行
                hr = sheet.createRow(rowNum);
            }
            return hr;
        }

        public Cell createCell(Row row, ExcelUnit eu) {
            Cell hc = row.createCell(eu.getCellNum());
            if (hc == null) return null;
            // 超链接
            if (eu.getHyperlink() != null) {
                hc.setHyperlink(eu.getHyperlink());
            }
            // 注释
            if (eu.getComment() != null) {
                hc.setCellComment(eu.getComment());
            }
            // 列样式
            if (eu.getCellStyle() == null) {
                eu.setCellStyle(createCellStyle(eu.getValue()));
            }
            if (eu.getCellStyle() != null) {
                hc.setCellStyle(eu.getCellStyle());
            }
            setValue(hc, eu.getValue());
            return hc;
        }

        public CellStyle createCellStyle(Object value) {
            if (value == null) return null;
            DataFormat df = wb.createDataFormat();
            CellStyle cs = wb.createCellStyle();
            cs.setFont(wb.createFont());
            if (value instanceof Number) {
                cs.setDataFormat(df.getFormat(NUMBER_FORMAT));
            } else if (value instanceof Date) {
                cs.setDataFormat(df.getFormat(DATE_FORMAT));
            } else if (value instanceof Calendar) {
                cs.setDataFormat(df.getFormat(DATE_FORMAT));
            }
            return cs;
        }

        /**
         * 设置一个单元格
         *
         * @param eu 单元格
         */
        public void setExcelUnit(ExcelUnit eu) {
            // 创建sheet
            Sheet sh = createSheet(eu.getSheetName());
            if (sh == null) return;
            // 合并单元格
            mergedCells(sh, eu);
            // 创建行
            Row hr = createRow(sh, eu.getRowNum());
            if (hr == null) return;
            createCell(hr, eu);
        }

        /**
         * 写入文件并关闭操作流
         *
         * @param fileName 文件名
         * @throws java.io.IOException
         */
        public void outStream(String fileName) throws IOException {
            if (LogicUtil.isEmpty(fileName)) return;
            outStream(new File(fileName));
        }

        /**
         * 写入文件并关闭操作流
         *
         * @param file File
         * @throws java.io.IOException
         */
        public void outStream(File file) throws IOException {
            if (file == null) return;
            OutputStream os = new FileOutputStream(file);
            try {
                outStream(os);
            } catch (IOException e) {
                logger.out(e);
                throw e;
            } finally {
                os.flush();
                os.close();
            }
        }

        /**
         * 写入到输出流中(注:不处理关闭流操作)
         *
         * @param os 输出流
         * @throws java.io.IOException
         */
        public void outStream(OutputStream os) throws IOException {
            if (os == null) return;
            try {
                wb.write(os);
            } catch (IOException e) {
                logger.out(e);
                throw e;
            }
        }
    }
}
