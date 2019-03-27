package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.query.BaseQuery;
import com.github.pagehelper.Page;


public interface BaseMapper<T> {
    Page<T> getListWithPage(BaseQuery query);
}
