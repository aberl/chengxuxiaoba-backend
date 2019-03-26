package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.po.Answer;
import com.chengxuxiaoba.video.model.po.Issue;
import com.chengxuxiaoba.video.service.IIssueService;

public class IssueService implements IIssueService {
    @Override
    public Boolean createNewIssue(Issue issue) {
        return null;
    }

    @Override
    public PageResult<Issue> getIssueListByVideoId(Integer videoId, PageInfo pageInfo) {
        return null;
    }

    @Override
    public PageResult<Issue> getIssueListByAccountId(Integer accountId, PageInfo pageInfo) {
        return null;
    }

    @Override
    public Issue getSingle(Integer issueId) {
        return null;
    }

    @Override
    public Boolean answerIssue(Answer answer) {
        return null;
    }

    @Override
    public PageResult<Answer> getAnswerListByVideoId(Integer issueId, PageInfo pageInfo) {
        return null;
    }
}
