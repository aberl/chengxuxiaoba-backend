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

    public static CommonStatus getEnum(Integer value){
        if(value == 1)
            return CommonStatus.ACTIVE;
        if(value == -1)
            return CommonStatus.INACTIVE;
        return null;
    }
}
