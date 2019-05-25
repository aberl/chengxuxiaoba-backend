package com.chengxuxiaoba.video.model.po;

public class VideoSummary {
    private Integer courseModuleId;
    private  Integer videoCount;
    private Integer totalViewCount;
    private Integer totalPraiseCount;

    public Integer getCourseModuleId() {
        return courseModuleId;
    }

    public void setCourseModuleId(Integer courseModuleId) {
        this.courseModuleId = courseModuleId;
    }

    public Integer getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }

    public Integer getTotalViewCount() {
        return totalViewCount;
    }

    public void setTotalViewCount(Integer totalViewCount) {
        this.totalViewCount = totalViewCount;
    }

    public Integer getTotalPraiseCount() {
        return totalPraiseCount;
    }

    public void setTotalPraiseCount(Integer totalPraiseCount) {
        this.totalPraiseCount = totalPraiseCount;
    }
}
