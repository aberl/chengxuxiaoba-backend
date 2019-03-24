package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.Evaluate;
import com.chengxuxiaoba.video.model.query.EvaluateQuery;
import com.github.pagehelper.Page;

import java.util.List;

public interface EvaluateMapper {
    Integer insert(Evaluate evaluate);

    Page<Evaluate> getEvaluateListWithPage(EvaluateQuery evaluateQuery);

    Evaluate getSingle(Integer id);

    Integer updateEvaluate(Evaluate evaluate);
}
