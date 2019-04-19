package com.chengxuxiaoba.video.constant;

public enum  CommonStatus {
    ACTIVE(1), INACTIVE(-1);

    private final Integer value;

    private CommonStatus(Integer value)
    {
        this.value=value;
    }

    public Integer getValue()
    {
        return value;
    }
}
