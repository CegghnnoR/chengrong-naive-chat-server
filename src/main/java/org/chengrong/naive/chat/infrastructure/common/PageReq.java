package org.chengrong.naive.chat.infrastructure.common;

import org.apache.commons.lang3.StringUtils;

public class PageReq {
    private int pageStart = 0;  //查询的起始索引，对应SQL语句中LIMIT子句的第一个参数
    private int pageEnd = 0;    //查询的最大返回行数，对应SQL语句中LIMIT子句的第二个参数

    private int page;  // 当前页码，用户请求的页数
    private int rows;  // 每页显示的行数，即每页数据的数量

    public PageReq() {
    }

    public PageReq(String page, String rows) {
        this.page = StringUtils.isEmpty(page) ? 1 : Integer.parseInt(page);
        this.rows = StringUtils.isEmpty(rows) ? 10 : Integer.parseInt(rows);
        if (this.page == 0) {
            this.page = 1;
        }
        this.pageStart = (this.page - 1) * this.rows;
        this.pageEnd = this.rows;
    }

    public void setPage(String page, String rows) {
        this.page = StringUtils.isEmpty(page) ? 1 : Integer.parseInt(page);
        this.rows = StringUtils.isEmpty(rows) ? 10 : Integer.parseInt(rows);
        if (this.page == 0) {
            this.page = 1;
        }
        this.pageStart = (this.page - 1) * this.rows;
        this.pageEnd = this.rows;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getPageEnd() {
        return pageEnd;
    }

    public void setPageEnd(int pageEnd) {
        this.pageEnd = pageEnd;
    }
}
