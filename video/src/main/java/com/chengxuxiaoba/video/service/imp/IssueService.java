package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.constant.IssueStatus;
import com.chengxuxiaoba.video.mapper.IssueMapper;
import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.ResultMessage;
import com.chengxuxiaoba.video.model.po.Account;
import com.chengxuxiaoba.video.model.po.Answer;
import com.chengxuxiaoba.video.model.po.Issue;
import com.chengxuxiaoba.video.model.po.Video;
import com.chengxuxiaoba.video.model.query.IssueQuery;
import com.chengxuxiaoba.video.service.IBaseService;
import com.chengxuxiaoba.video.service.IIssueService;
import com.chengxuxiaoba.video.service.IUserService;
import com.chengxuxiaoba.video.service.IVideoService;
import com.chengxuxiaoba.video.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class IssueService extends IBaseService<Issue> implements IIssueService {

    @Autowired
    private IVideoService videoService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IssueMapper issueMapper;

    @Override
    public KeyValuePair<Boolean, String> createNewIssue(Issue issue) {
        if (issue == null)
            return new KeyValuePair<Boolean, String>(false, ResultMessage.DataIsNULL);

        Video video = videoService.getSingle(issue.getVideoId());

        if (video == null)
            return new KeyValuePair<Boolean, String>(false, ResultMessage.VideoIsNotExist);

        Account account = userService.getUser(issue.getQuestionerId());

        if (account == null)
            return new KeyValuePair<Boolean, String>(false, ResultMessage.UserIsNotExist);

        Integer primaryKey = issueMapper.insertIssue(issue);

        Boolean flag = primaryKey > 0;

        return new KeyValuePair<Boolean, String>(flag, flag ? ResultMessage.Success : ResultMessage.Fail);
    }

    @Override
    public PageResult<Issue> getIssueListByVideoId(Integer videoId, PageInfo pageInfo) {
        if (videoId == null || pageInfo == null)
            return null;

        IssueQuery query = new IssueQuery();
        query.build(videoId, null, pageInfo);

        PageResult<Issue> pageResult = super.getListByQuery(issueMapper, query);

        return pageResult;
    }

    @Override
    public PageResult<Issue> getIssueListByAccountId(Integer accountId, PageInfo pageInfo) {
        if (accountId == null || pageInfo == null)
            return null;

        IssueQuery query = new IssueQuery();
        query.build(null, accountId, pageInfo);

        PageResult<Issue> pageResult = super.getListByQuery(issueMapper, query);

        return pageResult;
    }

    @Override
    public Issue getSingle(Integer issueId) {
        if (issueId == null || issueId == 0)
            return null;

        return issueMapper.getIssue(issueId);
    }

    @Override
    public KeyValuePair<Boolean, String> answerIssue(Answer answer) {
        if (answer == null || answer.getIssueId() == null)
            return new KeyValuePair<Boolean, String>(false, ResultMessage.DataIsNULL);

        if (answer.getIssueId() == 0)
            return new KeyValuePair<Boolean, String>(false, ResultMessage.IssueIsNotExist);

        Issue issue = issueMapper.getIssue(answer.getIssueId());

        if (issue == null)
            return new KeyValuePair<Boolean, String>(false, ResultMessage.IssueIsNotExist);

        if (issue.getStatus() != IssueStatus.ACTIVE.getValue())
            return new KeyValuePair<Boolean, String>(false, ResultMessage.IssueIsClosed);

        Integer totalAnswerCount=issue.getAnswerCount();
        issue.setAnswerCount(totalAnswerCount+1);

        Integer primaryKey = issueMapper.insertAnswer(answer);
        issueMapper.updateIssue(issue);

        Boolean flag = primaryKey > 0;

        if (!flag)
            return new KeyValuePair<Boolean, String>(flag, ResultMessage.Fail);

        return new KeyValuePair<Boolean, String>(flag, ResultMessage.Success);
    }

    @Override
    public List<Answer> getAnswerListByIssueId(Integer issueId) {
        if (issueId == null)
            return null;

        List<Answer> resultList = issueMapper.getAnswerList(Arrays.asList(issueId));
        return resultList;
    }

    @Override
    public List<Answer> getAnswerListByIssueIdList(List<Integer> issueIdList) {
        if (ListUtil.isNullOrEmpty(issueIdList))
            return null;

        List<Answer> resultList = issueMapper.getAnswerList(issueIdList);
        return resultList;
    }
}
