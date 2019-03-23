package com.chengxuxiaoba.video.model;

import com.chengxuxiaoba.video.util.StringUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
public class PageInfo<T> {
    private Integer currentPageNum;
    private Integer pageSize;
    private String sort;
    private Boolean isDesc;

    public void build(Integer currentPageNum, String sort, Boolean isDesc) {
        if (currentPageNum != null)
            this.currentPageNum = currentPageNum;

        if (sort != null)
            this.sort = sort;

        if (isDesc != null)
            this.isDesc = isDesc;
    }

    public void build(Integer pageSize)
    {
        if (pageSize != null)
            this.pageSize = pageSize;
        else
            this.pageSize= com.chengxuxiaoba.video.constant.PageInfo.defaultPageSize;
    }

    public void build(Integer currentPageNum, Integer pageSize, String sort, Boolean isDesc) {
        build(pageSize);

        build(currentPageNum,sort,isDesc);
    }

    public Integer getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(Integer currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Boolean getDesc() {
        if (this.isDesc == null)
            return false;
        return isDesc;
    }

    public void setDesc(Boolean desc) {
        isDesc = desc;
    }
}
