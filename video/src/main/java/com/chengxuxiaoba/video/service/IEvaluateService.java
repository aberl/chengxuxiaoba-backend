package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.po.Evaluate;

import java.util.List;

public interface IEvaluateService {
    Integer createNewEvaluate(Evaluate evaluate);

    PageResult<Evaluate> getEvaluateListByVideoId(Integer videoId, PageInfo pageInfo);

    PageResult<Evaluate> getEvaluateListByAccountId(Integer accountId, PageInfo pageInfo);

    Boolean givePraise(Integer Id);
}
