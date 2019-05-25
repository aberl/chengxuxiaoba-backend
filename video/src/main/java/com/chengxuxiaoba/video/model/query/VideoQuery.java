package com.chengxuxiaoba.video.model.query;

public class VideoQuery extends BaseQuery {
    private Integer courseModuleId;

    public Integer status;

    public Integer getCourseModuleId() {
        return courseModuleId;
    }

    public void setCourseModuleId(Integer courseModuleId) {
        this.courseModuleId = courseModuleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
