package org.core.bean;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Hyperlink;

/**
 * Excel单元格
 */
public class ExcelUnit {
    /**
     *  sheet名
     */
    private String sheetName;
    /**
     * 行号
     */
    private int rowNum;
    /**
     * 行合并数
     */
    private int rowMergedCount;
    /**
     * 列号
     */
    private int cellNum;
    /**
     * 行合并数
     */
    private int cellMergedCount;
    /**
     * 超链接
     */
    private Hyperlink hyperlink;
    /**
     * 注释
     */
    private Comment comment;
    /**
     * 列样式
     */
    private CellStyle cellStyle;
    /**
     * 单元格值
     */
    private Object value;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getCellNum() {
        return cellNum;
    }

    public void setCellNum(int cellNum) {
        this.cellNum = cellNum;
    }

    public Hyperlink getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(Hyperlink hyperlink) {
        this.hyperlink = hyperlink;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public CellStyle getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getRowMergedCount() {
        return rowMergedCount;
    }

    public void setRowMergedCount(int rowMergedCount) {
        this.rowMergedCount = rowMergedCount;
    }

    public int getCellMergedCount() {
        return cellMergedCount;
    }

    public void setCellMergedCount(int cellMergedCount) {
        this.cellMergedCount = cellMergedCount;
    }
}
