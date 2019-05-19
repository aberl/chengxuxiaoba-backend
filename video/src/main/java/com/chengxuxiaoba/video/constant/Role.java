package com.chengxuxiaoba.video.constant;

public enum Role {
    ADMIN(1), MEMBER(2), VIPMEMBER(3);

    private final Integer value;

    private Role(Integer value)
    {
        this.value=value;
    }

    public Integer getValue()
    {
        return value;
    }

    public static Role getEnum(Integer value){
        if(value == 1)
            return Role.ADMIN;
        if(value == 2)
            return Role.MEMBER;
        if(value == 3)
            return Role.VIPMEMBER;
        return null;
    }
}
