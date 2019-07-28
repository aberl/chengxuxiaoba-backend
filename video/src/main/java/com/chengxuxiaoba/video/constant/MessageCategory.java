package com.chengxuxiaoba.video.constant;

public enum  MessageCategory {
    System(0), User(1);

    private final Integer value;

    MessageCategory(Integer value)
    {
        this.value=value;
    }

    public Integer getValue()
    {
        return value;
    }
    public static MessageCategory getEnum(Integer value){
        if(value == 0)
            return MessageCategory.System;
        if(value == 1)
            return MessageCategory.User;
        return null;
    }

    public static String getText(MessageCategory messageCategory)
    {
        if(messageCategory == MessageCategory.System)
            return "系统消息";

        if(messageCategory == MessageCategory.User)
            return "用户消息";

        return null;
    }
}
