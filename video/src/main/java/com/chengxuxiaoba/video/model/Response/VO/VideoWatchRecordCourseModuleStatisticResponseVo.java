package com.chengxuxiaoba.video.model.Response.VO;

public class VideoWatchRecordCourseModuleStatisticResponseVo {
    private String courseName;
    private Integer courseModuleId;
    private Integer videoStatisticCount;
    private String courseModuleName;
    private Integer totalcourseModuleVideoCount;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

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

    public Integer getTotalcourseModuleVideoCount() {
        return totalcourseModuleVideoCount;
    }

    public void setTotalcourseModuleVideoCount(Integer totaCcourseModuleVideoCount) {
        this.totalcourseModuleVideoCount = totaCcourseModuleVideoCount;
    }
}
