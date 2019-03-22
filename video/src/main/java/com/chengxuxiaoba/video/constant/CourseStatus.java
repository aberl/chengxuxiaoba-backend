package com.chengxuxiaoba.video.constant;


public enum  CourseStatus {
    ACTIVE(1), INACTIVE(-1);

    private final Integer value;

    private CourseStatus(Integer value)
    {
        this.value=value;
    }

    public Integer getValue()
    {
        return value;
    }
}
