package com.chengxuxiaoba.video.model.Response.VO;

import java.util.Date;

public class IssueResponseVo {
    private Integer id;
    private Integer videoId;
    private String name;
    private String content;
    private Integer questionerId;
    private Integer status;
    private Date createDateTime;

    private UserResponseVo userResponseVo;

    private Integer answerCount;

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

    public UserResponseVo getUserResponseVo() {
        return userResponseVo;
    }

    public void setUserResponseVo(UserResponseVo userResponseVo) {
        this.userResponseVo = userResponseVo;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }
}
