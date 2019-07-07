package com.chengxuxiaoba.video.model.po;

import java.util.Date;

public class VideoWatchRecord {
    private Integer id;
    private Integer courseModuleId;
    private Integer videoId;
    private Integer accountId;
    private Date lastWatchTime;

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

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Date getLastWatchTime() {
        return lastWatchTime;
    }

    public void setLastWatchTime(Date lastWatchTime) {
        this.lastWatchTime = lastWatchTime;
    }
}
