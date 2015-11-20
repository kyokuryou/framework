package org.smarty.core.bean;

import java.util.List;
import java.util.Map;

/**
 * 分页
 * Created Date 2015/04/09
 *
 * @author quliang
 * @version 1.0
 */
public class Pager {

    /**
     * 排序方式
     */
    public enum OrderType {
        asc, desc
    }

    /**
     * 每页最大记录数限制
     */
    public static final Integer MAX_PAGE_SIZE = 500;

    /**
     * 当前页码
     */
    private int pageNumber = 1;
    /**
     * 每页记录数
     */
    private int pageSize = 20;
    /**
     * 总记录数
     */
    private long totalCount = 0L;
    /**
     * 总页数
     */
    private long pageCount = 0L;
    /**
     * 参数
     */
    private Map<String, String> params;
    /**
     * 排序字段
     */
    private String orderBy;
    /**
     * 排序方式
     */
    private OrderType orderType = OrderType.desc;
    /**
     * 数据List
     */
    private List<?> list;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            pageSize = 1;
        } else if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }

    public static int getMaxPageSize() {
        return MAX_PAGE_SIZE;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getPageCount() {
        pageCount = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            pageCount++;
        }
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}