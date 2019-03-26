package com.chengxuxiaoba.video.model.po;

import java.util.Date;

public class Issue {
    private Integer id;
    private Integer videoId;
    private String name;
    private String content;
    private Integer questionerId;
    private Integer status;
    private Date createDateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

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

    public Integer getQuestionerId() {
        return questionerId;
    }

    public void setQuestionerId(Integer questionerId) {
        this.questionerId = questionerId;
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
