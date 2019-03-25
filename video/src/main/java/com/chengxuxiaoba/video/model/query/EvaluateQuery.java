package com.chengxuxiaoba.video.model.query;

import com.chengxuxiaoba.video.model.PageInfo;

public class EvaluateQuery {
    private Integer id;
    private Integer accountId;
    private Integer videoId;

    private PageInfo pageInfo;

    public EvaluateQuery() {
    }

    public EvaluateQuery(Integer id, Integer accountId, Integer videoId) {
        this.id = id;
        this.accountId = accountId;
        this.videoId = videoId;
    }

    public void build(Integer id, Integer accountId, Integer videoId, PageInfo pageInfo)
    {
        this.id = id;
        this.accountId = accountId;
        this.videoId = videoId;
        this.pageInfo=pageInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
