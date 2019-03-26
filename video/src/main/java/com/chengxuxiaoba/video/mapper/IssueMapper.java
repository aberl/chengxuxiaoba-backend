package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.Answer;
import com.chengxuxiaoba.video.model.po.Issue;
import com.chengxuxiaoba.video.model.query.IssueQuery;

import java.util.List;

public interface IssueMapper {
    Integer insertIssue(Issue issue);

    Integer insertAnswer(Answer answer);

    List<Issue> getIssueList(IssueQuery issueQuery);

    List<Answer> getAnswerList(Integer issueId);
}
