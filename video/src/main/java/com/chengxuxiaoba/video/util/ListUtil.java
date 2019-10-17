package com.chengxuxiaoba.video.util;

import java.util.List;

public class ListUtil {
    public static Boolean isNullOrEmpty(List list)
    {
        if(list == null)
            return true;

        if(list.size()==0)
            return true;

        return false;
    }

    public static Boolean isNotNullOrEmpty(List list)
    {
        return !isNullOrEmpty(list);
    }
}
