package com.xingye.pojo;

import java.util.List;

public class PageResult<T> {
    // 总记录数
    private long total;
    // 总页数
    private int totalPages;
    // 当前页码
    private int pageNum;
    // 每页条数
    private int pageSize;
    // 数据列表
    private List<T> list;

    public PageResult(long total, int pageNum, int pageSize, List<T> list) {
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.list = list;
        // 计算总页数
        this.totalPages = (int) Math.ceil((double) total / pageSize);
    }

    // getter和setter方法
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
