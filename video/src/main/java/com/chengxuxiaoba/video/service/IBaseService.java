package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.mapper.BaseMapper;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.query.BaseQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

public abstract class IBaseService<T> {
    public PageResult<T> getListByQuery(BaseMapper mapper, BaseQuery query) {
        if (query == null)
            return null;

        PageHelper.startPage(query.getPageInfo().getCurrentPageNum(), query.getPageInfo().getPageSize());

        PageHelper.orderBy(query.getPageInfo().getSort());

        Page<T> retList = mapper.getListWithPage(query);

        PageResult<T> pageResult = new PageResult<T>(retList.getPageNum(), retList.getTotal(), retList.getResult());

        return pageResult;
    }


}
