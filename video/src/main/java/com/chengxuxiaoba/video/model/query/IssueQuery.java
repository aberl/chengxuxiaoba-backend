package com.chengxuxiaoba.video.model.query;

import com.chengxuxiaoba.video.model.PageInfo;

public class IssueQuery extends BaseQuery{
    private Integer videoId;
    private Integer accountId;

    public void build(Integer videoId, Integer accountId, PageInfo pageInfo){
        this.videoId=videoId;
        this.accountId=accountId;
        super.setPageInfo(pageInfo);
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
