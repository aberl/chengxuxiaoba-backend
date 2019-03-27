package com.chengxuxiaoba.video.constant;

public enum  IssueStatus {
    ACTIVE(1), CLOSED(-1);

    private final Integer value;

    private IssueStatus(Integer value)
    {
        this.value=value;
    }

    public Integer getValue()
    {
        return value;
    }
}
