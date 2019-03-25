package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.handler.Handler;
import com.chengxuxiaoba.video.model.PageInfo;

public class BaseController<T> {
    public PageInfo generatePageInfo(Integer pageNum, Integer pageSize, String sort, Boolean isDesc)
    {
        PageInfo pageInfo = new PageInfo();
        pageInfo.build(pageNum, pageSize, Handler.handleRestAPISort(sort), null);

        return pageInfo;
    }

    public PageInfo generatePageInfo(Integer pageNum, Integer pageSize, String sort)
    {
        return generatePageInfo(pageNum, pageSize, sort, null);
    }
}
