package com.chengxuxiaoba.video.constant;


public enum CourseModuleStatus {
    ACTIVE(1), INACTIVE(-1);

    private final Integer value;

    private CourseModuleStatus(Integer value)
    {
        this.value=value;
    }

    public Integer getValue()
    {
        return value;
    }
}
