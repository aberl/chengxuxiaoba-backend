package com.chengxuxiaoba.video.model;

import java.util.List;

public class PageResult<T> {
    private  Integer currentNum;
    private  Long totalCount;
    private List<T> data;

    public PageResult(Integer currentNum, Long totalCount, List<T> data){
        this.currentNum=currentNum;
        this.totalCount=totalCount;
        this.data=data;
    }

    public Integer getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(Integer currentNum) {
        this.currentNum = currentNum;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
