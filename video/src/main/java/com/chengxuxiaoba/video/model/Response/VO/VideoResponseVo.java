package com.chengxuxiaoba.video.model.Response.VO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Map;
import java.util.List;

public class VideoResponseVo {
    private Integer id;
    private Integer courseModuleId;
    private UploadFileResponseVo video;
    private String name;
    private List<UploadFileResponseVo> attachmentList;
    private String duration;
    private String description;
    private String images;
    private Integer viewCount;
    private Integer praiseCount;
    private Integer status;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private Date createDateTime;
    private String statusDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseModuleId() {
        return courseModuleId;
    }

    public void setCourseModuleId(Integer courseModuleId) {
        this.courseModuleId = courseModuleId;
    }

    public UploadFileResponseVo getVideo() {
        return video;
    }

    public void setVideo(UploadFileResponseVo video) {
        this.video = video;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
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

    public List<UploadFileResponseVo> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<UploadFileResponseVo> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
