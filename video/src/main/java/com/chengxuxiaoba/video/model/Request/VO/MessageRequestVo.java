package com.chengxuxiaoba.video.model.Request.VO;

import java.util.List;

public class MessageRequestVo {
    private String name;
    private String content;
    private Integer category;
    private Boolean broadcast;
    private List<Integer> messageIdList;
    private Integer accountId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Boolean getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(Boolean broadcast) {
        this.broadcast = broadcast;
    }

    public List<Integer> getMessageIdList() {
        return messageIdList;
    }

    public void setMessageIdList(List<Integer> messageIdList) {
        this.messageIdList = messageIdList;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
