package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.po.Answer;
import com.chengxuxiaoba.video.model.po.Evaluate;
import com.chengxuxiaoba.video.model.po.Issue;


public interface IIssueService {
    Boolean createNewIssue(Issue issue);

    PageResult<Issue> getIssueListByVideoId(Integer videoId, PageInfo pageInfo);

    PageResult<Issue> getIssueListByAccountId(Integer accountId, PageInfo pageInfo);

    Issue getSingle(Integer issueId);

    Boolean answerIssue(Answer answer);

    PageResult<Answer> getAnswerListByVideoId(Integer issueId, PageInfo pageInfo);
}
