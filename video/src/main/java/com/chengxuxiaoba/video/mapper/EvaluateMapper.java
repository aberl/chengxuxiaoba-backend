package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.Evaluate;
import com.chengxuxiaoba.video.model.query.EvaluateQuery;
import com.github.pagehelper.Page;

import java.util.List;

public interface EvaluateMapper extends BaseMapper<Evaluate> {
    Integer insert(Evaluate evaluate);

    Evaluate getSingle(Integer id);

    Integer updateEvaluate(Evaluate evaluate);
}
