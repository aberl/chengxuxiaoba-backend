package com.chengxuxiaoba.video.constant;

public enum  MessageCategory {
    System(0), User(1);

    private final Integer value;

    private MessageCategory(Integer value)
    {
        this.value=value;
    }

    public Integer getValue()
    {
        return value;
    }
}
