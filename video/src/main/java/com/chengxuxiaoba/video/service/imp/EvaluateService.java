package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.mapper.EvaluateMapper;
import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.po.Evaluate;
import com.chengxuxiaoba.video.model.query.EvaluateQuery;
import com.chengxuxiaoba.video.service.IBaseService;
import com.chengxuxiaoba.video.service.IEvaluateService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluateService extends IBaseService<Evaluate> implements IEvaluateService {
    @Autowired
    private EvaluateMapper evaluateMapper;

    @Override
    public Boolean createNewEvaluate(Evaluate evaluate) {
        Integer primaryKey = evaluateMapper.insert(evaluate);

        return primaryKey>0;
    }

    @Override
    public Evaluate getSingle(Integer evaluationId) {
        if (evaluationId == null || evaluationId == 0)
            return null;

        return evaluateMapper.getSingle(evaluationId);
    }

    @Override
    public Boolean updateEvaluate(Evaluate evaluate) {
        if (evaluate == null)
            return false;

        Integer primyKey = evaluateMapper.updateEvaluate(evaluate);

        return primyKey>0;
    }

    @Override
    public PageResult<Evaluate> getEvaluateListByVideoId(Integer videoId, PageInfo pageInfo) {
        if (videoId == null || pageInfo == null)
            return null;

        EvaluateQuery query = new EvaluateQuery();
        query.build(null,null,videoId,pageInfo);
        PageResult<Evaluate> pageResult =  super.getListByQuery(evaluateMapper, query);

        return pageResult;
    }

    @Override
    public PageResult<Evaluate> getEvaluateListByAccountId(Integer accountId, PageInfo pageInfo) {
        if (accountId == null || pageInfo == null)
            return null;

        EvaluateQuery query = new EvaluateQuery();
        query.build(null,accountId,null,pageInfo);

        PageResult<Evaluate> pageResult =  super.getListByQuery(evaluateMapper, query);

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

    @Override
    public Boolean isExist(EvaluateQuery query) {
        if(query == null)
            return false;

        PageInfo pageInfo=new PageInfo();
        pageInfo.build(1,1,null,null);
        query.setPageInfo(pageInfo);

        PageResult<Evaluate> pageResult = super.getListByQuery(evaluateMapper, query);

        if(pageResult == null)
            return false;

        return pageResult.getTotalCount()>0;
    }
}
