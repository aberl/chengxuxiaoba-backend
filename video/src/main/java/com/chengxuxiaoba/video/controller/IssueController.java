package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.annotation.AuthorizationValidation;
import com.chengxuxiaoba.video.constant.CommonStatus;
import com.chengxuxiaoba.video.model.*;
import com.chengxuxiaoba.video.model.Request.VO.AnswerRequestVo;
import com.chengxuxiaoba.video.model.Request.VO.IssueRequestVo;
import com.chengxuxiaoba.video.model.Response.VO.AnswerResponseVo;
import com.chengxuxiaoba.video.model.Response.VO.IssueResponseVo;
import com.chengxuxiaoba.video.model.Response.VO.VideoResponseVo;
import com.chengxuxiaoba.video.model.po.Answer;
import com.chengxuxiaoba.video.model.po.Issue;
import com.chengxuxiaoba.video.service.IAuthenticationService;
import com.chengxuxiaoba.video.service.IIssueService;
import com.chengxuxiaoba.video.service.IVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class IssueController extends BaseController {

    @Autowired
    private IIssueService issueService;
    @Autowired
    private IVoService voService;

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/videos/issues")
    @AuthorizationValidation()
    public Result<Boolean> createIssue(@RequestBody IssueRequestVo requestBody) throws IOException {
        CurrentLoginUserModel currentLoginUserModel = authenticationService.getCurrentLoginUserModelFromRequest();

        Issue issue = voService.convertToIssue(requestBody);
        issue.setQuestionerId(currentLoginUserModel.getUserId());

        KeyValuePair<Boolean, String> result = issueService.createNewIssue(issue);

        return new Result<Boolean>(ResultCode.Success, result.getKey(), result.getValue());
    }

    @GetMapping("/video/issues/{issueId}")
    public Result<IssueResponseVo> getIssueListByUserId(@PathVariable("issueId") Integer issueId) {
        if (issueId == null || issueId == 0)
            return new Result<IssueResponseVo>(ResultCode.Error, null, ResultMessage.ParameterError);

        Issue issue = issueService.getSingle(issueId);

        IssueResponseVo issueResponseVo = voService.convertIssueResponseVo(issue);

        return new Result<IssueResponseVo>(ResultCode.Success, issueResponseVo, ResultMessage.Success);
    }

    @GetMapping("/videos/{videoId}/issues")
    public Result<PageResult<IssueResponseVo>> getIssueListByVideoId(@PathVariable("videoId") Integer videoId,
                                                                     @RequestParam("pagenum") Integer pageNum,
                                                                     @RequestParam(name = "pagesize", required = false) Integer pageSize,
                                                                     @RequestParam(name = "sort", required = false) String sort) {
        if (videoId == null || videoId == 0)
            return new Result<PageResult<IssueResponseVo>>(ResultCode.Error, null, ResultMessage.ParameterError);


        PageInfo pageInfo = super.generatePageInfo(pageNum, pageSize, sort);

        PageResult<Issue> pageData = issueService.getIssueListByVideoId(videoId, pageInfo);

        List<IssueResponseVo> resultList = voService.convertIssueResponseVo(pageData.getData());

        PageResult<IssueResponseVo> result = new PageResult<IssueResponseVo>(pageData.getCurrentNum(), pageData.getTotalCount(), resultList);

        return new Result<PageResult<IssueResponseVo>>(ResultCode.Success, result, ResultMessage.Success);
    }

    @GetMapping("/issues")
    @AuthorizationValidation()
    public Result<PageResult<IssueResponseVo>> getIssueListByUserId(@RequestParam("pagenum") Integer pageNum,
                                                                    @RequestParam(name = "pagesize", required = false) Integer pageSize,
                                                                    @RequestParam(name = "sort", required = false) String sort) {
        CurrentLoginUserModel currentLoginUserModel = authenticationService.getCurrentLoginUserModelFromRequest();

        PageInfo pageInfo = super.generatePageInfo(pageNum, pageSize, sort);

        PageResult<Issue> pageData = issueService.getIssueListByAccountId(currentLoginUserModel.getUserId(), pageInfo);

        List<IssueResponseVo> resultList = voService.convertIssueResponseVo(pageData.getData());

        PageResult<IssueResponseVo> result = new PageResult<IssueResponseVo>(pageData.getCurrentNum(), pageData.getTotalCount(), resultList);

        return new Result<PageResult<IssueResponseVo>>(ResultCode.Success, result, ResultMessage.Success);
    }

    @PostMapping("/answer")
    @AuthorizationValidation()
    public Result<Boolean> answerIssue(@RequestBody AnswerRequestVo requestBody) throws IOException {
        CurrentLoginUserModel currentLoginUserModel = authenticationService.getCurrentLoginUserModelFromRequest();

        Answer answer = voService.convertToAnswer(requestBody);
        answer.setAnswererId(currentLoginUserModel.getUserId());

        KeyValuePair<Boolean, String> result = issueService.answerIssue(answer);

        return new Result<Boolean>(ResultCode.Success, result.getKey(), result.getValue());
    }

    @GetMapping("/issues/{issueId}/answers")
    public Result<List<AnswerResponseVo>> getAnswerListByIssueId(@PathVariable("issueId") Integer issueId) {
        if (issueId == null || issueId == 0)
            return new Result<List<AnswerResponseVo>>(ResultCode.Error, null, ResultMessage.ParameterError);

        List<Answer> answerList = issueService.getAnswerListByIssueId(issueId);

        List<AnswerResponseVo> resultList = voService.convertAnswerResponseVo(answerList);

        return new Result<List<AnswerResponseVo>>(ResultCode.Success, resultList, ResultMessage.Success);
    }
}
