package com.chengxuxiaoba.video.model.Response.VO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class CourseModuleResponseVo {
    private Integer id;
    private Integer courseId;
    private String name;
    private String courseName;
    private String description;
    private List<UploadFileResponseVo> imageList;
    private Integer status;
    private String statusDesc;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private Date createDateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UploadFileResponseVo> getImageList() {
        return imageList;
    }

    public void setImageList(List<UploadFileResponseVo> imageList) {
        this.imageList = imageList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }
}
