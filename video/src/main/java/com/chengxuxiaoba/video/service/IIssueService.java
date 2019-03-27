package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.po.Answer;
import com.chengxuxiaoba.video.model.po.Issue;

import java.util.List;


public interface IIssueService {
    KeyValuePair<Boolean, String> createNewIssue(Issue issue);

    PageResult<Issue> getIssueListByVideoId(Integer videoId, PageInfo pageInfo);

    PageResult<Issue> getIssueListByAccountId(Integer accountId, PageInfo pageInfo);

    Issue getSingle(Integer issueId);

    KeyValuePair<Boolean, String> answerIssue(Answer answer);

    List<Answer> getAnswerListByVideoId(Integer issueId);
}
