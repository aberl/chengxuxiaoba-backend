package com.chengxuxiaoba.video.model.po;

import java.util.Date;

public class Answer {
    private Integer id;
    private Integer issueId;
    private String content;
    private Integer answererId;
    private Integer status;
    private Date createDateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAnswererId() {
        return answererId;
    }

    public void setAnswererId(Integer answererId) {
        this.answererId = answererId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }
}
