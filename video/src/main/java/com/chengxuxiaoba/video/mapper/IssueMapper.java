package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.Answer;
import com.chengxuxiaoba.video.model.po.Issue;
import com.chengxuxiaoba.video.model.query.IssueQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IssueMapper extends  BaseMapper<Issue>{
    Integer insertIssue(Issue issue);

    Integer insertAnswer(Answer answer);

    Issue getIssue(Integer id);

    List<Answer> getAnswerList(@Param("issueIdList") List<Integer> issueIdList);

    Integer updateIssue(Issue issue);
}
