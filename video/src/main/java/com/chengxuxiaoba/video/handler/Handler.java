package com.chengxuxiaoba.video.handler;

import com.chengxuxiaoba.video.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Handler {
    /**
     * handle sort to be sql sort string, if format is -xxx than DESC else ASC
     * @param sort
     * @return
     */
    public static String handleRestAPISort(String sort){
        if(StringUtil.isNullOrEmpty(sort))
            return sort;

        if(sort.length() == 1)
            return sort;

        String first=sort.substring(0,1);
        String left = sort.substring(1);

        if(first.equals("-"))
            return String.format("%s %s",left ,"DESC");

        if(first.equals("+"))
            return String.format("%s %s",left ,"ASC");

        return String.format("%s %s",sort ,"ASC");
    }
}
