package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.mapper.EvaluateMapper;
import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.po.Evaluate;
import com.chengxuxiaoba.video.model.po.Video;
import com.chengxuxiaoba.video.model.query.EvaluateQuery;
import com.chengxuxiaoba.video.service.IEvaluateService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluateService implements IEvaluateService {
    @Autowired
    private EvaluateMapper evaluateMapper;

    @Override
    public Integer createNewEvaluate(Evaluate evaluate) {
        return evaluateMapper.insert(evaluate);
    }

    @Override
    public PageResult<Evaluate> getEvaluateListByVideoId(Integer videoId, PageInfo pageInfo) {
        if (videoId == null || pageInfo == null)
            return null;

        EvaluateQuery query = new EvaluateQuery();
        query.setVideoId(videoId);
        query.setPageInfo(pageInfo);
        PageResult<Evaluate> pageResult = getEvaluateListByQuery(query);

        return pageResult;
    }

    @Override
    public PageResult<Evaluate> getEvaluateListByAccountId(Integer accountId, PageInfo pageInfo) {
        if (accountId == null || pageInfo == null)
            return null;

        EvaluateQuery query = new EvaluateQuery();
        query.setVideoId(accountId);
        query.setPageInfo(pageInfo);
        PageResult<Evaluate> pageResult = getEvaluateListByQuery(query);

        return pageResult;
    }

    @Override
    public Boolean givePraise(Integer id) {
        Evaluate evaluate = evaluateMapper.getSingle(id);
        if (evaluate == null)
            return false;

        Integer praiseCount = evaluate.getPraiseCount() == null ? 1 : evaluate.getPraiseCount() + 1;

        evaluate.setPraiseCount(praiseCount);

        Integer primaryKey = evaluateMapper.updateEvaluate(evaluate);

        return primaryKey > 0;
    }

    private PageResult<Evaluate> getEvaluateListByQuery(EvaluateQuery query) {
        if (query == null)
            return null;

        PageHelper.startPage(query.getPageInfo().getCurrentPageNum() - 1, query.getPageInfo().getPageSize());

        PageHelper.orderBy(query.getPageInfo().getSort());

        Page<Evaluate> retList = evaluateMapper.getEvaluateListWithPage(query);

        PageResult<Evaluate> pageResult = new PageResult<Evaluate>(retList.getPageNum(), retList.getTotal(), retList.getResult());

        return pageResult;
    }
}
