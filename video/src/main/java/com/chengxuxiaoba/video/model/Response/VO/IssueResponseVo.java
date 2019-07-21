package com.chengxuxiaoba.video.model.Response.VO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class IssueResponseVo {
    private Integer id;
    private Integer videoId;
    private String name;
    private String content;
    private Integer questionerId;
    private Integer status;
    private String statusDesc;
    private Integer answerCount;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private Date createDateTime;

    private UserResponseVo userResponseVo;
    private List<AnswerResponseVo> answerResponseVoList;


    private String videoName;

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

    public List<AnswerResponseVo> getAnswerResponseVoList() {
        return answerResponseVoList;
    }

    public void setAnswerResponseVoList(List<AnswerResponseVo> answerResponseVoList) {
        this.answerResponseVoList = answerResponseVoList;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
}
