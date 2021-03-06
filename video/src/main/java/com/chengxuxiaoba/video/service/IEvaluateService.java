package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.constant.CommonStatus;
import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.po.Evaluate;
import com.chengxuxiaoba.video.model.query.EvaluateQuery;

public interface IEvaluateService{
    Boolean createNewEvaluate(Evaluate evaluate);

    Evaluate getSingle(Integer evaluationId);

    Boolean updateEvaluate(Evaluate evaluate);

    PageResult<Evaluate> getEvaluateListByVideoId(Integer videoId, PageInfo pageInfo);

    PageResult<Evaluate> getEvaluateList(Integer videoId, CommonStatus status, PageInfo pageInfo);

    PageResult<Evaluate> getEvaluateListByAccountId(Integer accountId, PageInfo pageInfo);

    Boolean givePraise(Integer Id);

    Boolean isExist(EvaluateQuery query);
}
