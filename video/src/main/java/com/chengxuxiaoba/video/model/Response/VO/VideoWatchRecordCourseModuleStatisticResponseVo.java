package com.chengxuxiaoba.video.model.Response.VO;

public class VideoWatchRecordCourseModuleStatisticResponseVo {
    private Integer courseModuleId;
    private Integer videoStatisticCount;
    private String courseModuleName;
    private Integer totaCcourseModuleVideoCount;

    public Integer getCourseModuleId() {
        return courseModuleId;
    }

    public void setCourseModuleId(Integer courseModuleId) {
        this.courseModuleId = courseModuleId;
    }

    public Integer getVideoStatisticCount() {
        return videoStatisticCount;
    }

    public void setVideoStatisticCount(Integer videoStatisticCount) {
        this.videoStatisticCount = videoStatisticCount;
    }

    public String getCourseModuleName() {
        return courseModuleName;
    }

    public void setCourseModuleName(String courseModuleName) {
        this.courseModuleName = courseModuleName;
    }

    public Integer getTotaCcourseModuleVideoCount() {
        return totaCcourseModuleVideoCount;
    }

    public void setTotaCcourseModuleVideoCount(Integer totaCcourseModuleVideoCount) {
        this.totaCcourseModuleVideoCount = totaCcourseModuleVideoCount;
    }
}
