package com.chengxuxiaoba.video.util;

public class ArrayUtil {
    public static Boolean isNullOrEmpty(Object[] array)
    {
        if(array == null)
            return true;

        if(array.length==0)
            return true;

        return false;
    }
}
