package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.annotation.AuthorizationValidation;
import com.chengxuxiaoba.video.constant.CommonStatus;
import com.chengxuxiaoba.video.handler.Handler;
import com.chengxuxiaoba.video.model.*;
import com.chengxuxiaoba.video.model.Request.VO.*;
import com.chengxuxiaoba.video.model.Response.VO.EvaluateResponseVo;
import com.chengxuxiaoba.video.model.Response.VO.VideoResponseVo;
import com.chengxuxiaoba.video.model.po.Account;
import com.chengxuxiaoba.video.model.po.Evaluate;
import com.chengxuxiaoba.video.model.po.Issue;
import com.chengxuxiaoba.video.model.po.Video;
import com.chengxuxiaoba.video.model.query.EvaluateQuery;
import com.chengxuxiaoba.video.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class EvaluateController extends BaseController {
    @Autowired
    private IVoService voService;

    @Autowired
    private IVideoService videoService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IEvaluateService evaluatevoService;

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/videos/evaluates")
    @AuthorizationValidation()
    public Result<Boolean> createEvaluate(@RequestBody EvaluateRequestVo requestBody) {
        CurrentLoginUserModel currentLoginUserModel= authenticationService.getCurrentLoginUserModelFromRequest();

        Video video = videoService.getSingle(requestBody.getVideoId());
        if (video == null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.VideoIsNotExist);

        Account account = userService.getUser(currentLoginUserModel.getUserId());
        if (account == null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.UserIsNotExist);

        EvaluateQuery evaluateQuery = new EvaluateQuery();
        evaluateQuery.setVideoId(requestBody.getVideoId());
        evaluateQuery.setAccountId(currentLoginUserModel.getUserId());

        if (evaluatevoService.isExist(evaluateQuery))
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.CannotEvluateOneVideoMoreTime);

        Evaluate evaluate = voService.convertToEvalueate(requestBody);
        evaluate.setAccountId(currentLoginUserModel.getUserId());
        Boolean flag = evaluatevoService.createNewEvaluate(evaluate);

        if (!flag)
            return new Result<Boolean>(ResultCode.Error, flag, ResultMessage.Fail);

        return new Result<Boolean>(ResultCode.Success, flag, ResultMessage.Success);
    }

    @GetMapping("/videos/{videoId}/evaluates")
    public Result<PageResult<EvaluateResponseVo>> getEvaluateByVideoId(@PathVariable("videoId") Integer videoId,
                                                                       @RequestParam("pagenum") Integer pageNum,
                                                                       @RequestParam(name = "pagesize", required = false) Integer pageSize,
                                                                       @RequestParam(name = "sort", required = false) String sort) {
        if (videoId == null || videoId == 0)
            return new Result<PageResult<EvaluateResponseVo>>(ResultCode.Error, null, ResultMessage.ParameterError);

        PageInfo pageInfo = super.generatePageInfo(pageNum, pageSize, sort);

        PageResult<Evaluate> pageData = evaluatevoService.getEvaluateListByVideoId(videoId, pageInfo);

        List<EvaluateResponseVo> resultList = voService.convertToEvaluateResponseVo(pageData.getData());

        PageResult<EvaluateResponseVo> result = new PageResult<EvaluateResponseVo>(pageData.getCurrentNum(), pageData.getTotalCount(), resultList);

        return new Result<PageResult<EvaluateResponseVo>>(ResultCode.Success, result, ResultMessage.Success);
    }

    @GetMapping("/videos/{videoId}/evaluates/effective")
    public Result<PageResult<EvaluateResponseVo>> getAllEffectiveEvaluateList(@PathVariable("videoId") Integer videoId,
                                                                       @RequestParam("pagenum") Integer pageNum,
                                                                       @RequestParam(name = "pagesize", required = false) Integer pageSize,
                                                                       @RequestParam(name = "sort", required = false) String sort) {
        if (videoId == null || videoId == 0)
            return new Result<PageResult<EvaluateResponseVo>>(ResultCode.Error, null, ResultMessage.ParameterError);

        PageInfo pageInfo = super.generatePageInfo(pageNum, pageSize, sort);

        PageResult<Evaluate> pageData = evaluatevoService.getEvaluateList(videoId, CommonStatus.ACTIVE,pageInfo);

        List<EvaluateResponseVo> resultList = voService.convertToEvaluateResponseVo(pageData.getData());

        PageResult<EvaluateResponseVo> result = new PageResult<EvaluateResponseVo>(pageData.getCurrentNum(), pageData.getTotalCount(), resultList);

        return new Result<PageResult<EvaluateResponseVo>>(ResultCode.Success, result, ResultMessage.Success);
    }

    @GetMapping("/evaluates")
    @AuthorizationValidation()
    public Result<PageResult<EvaluateResponseVo>> getEvaluateByAccountId(@RequestParam("pagenum") Integer pageNum,
                                                                         @RequestParam(name = "pagesize", required = false) Integer pageSize,
                                                                         @RequestParam(name = "sort", required = false) String sort) {
        CurrentLoginUserModel currentLoginUserModel= authenticationService.getCurrentLoginUserModelFromRequest();

        PageInfo pageInfo = super.generatePageInfo(pageNum, pageSize, sort);

        PageResult<Evaluate> pageData = evaluatevoService.getEvaluateListByAccountId(currentLoginUserModel.getUserId(), pageInfo);

        List<EvaluateResponseVo> resultList = voService.convertToEvaluateResponseVo(pageData.getData());

        PageResult<EvaluateResponseVo> result = new PageResult<EvaluateResponseVo>(pageData.getCurrentNum(), pageData.getTotalCount(), resultList);

        return new Result<PageResult<EvaluateResponseVo>>(ResultCode.Success, result, ResultMessage.Success);
    }

    @DeleteMapping("/videos/evaluate/{id}")
    public Result<Boolean> createIssue(@PathVariable("id") Integer id) throws IOException {
        if (id == null || id == 0)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.ParameterError);

        Evaluate evaluate = evaluatevoService.getSingle(id);

        if (evaluate == null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.EvaluationIsNotExist);

        evaluate.setStatus(CommonStatus.INACTIVE.getValue());
        Boolean flag = evaluatevoService.updateEvaluate(evaluate);
        return new Result<Boolean>(ResultCode.Success, flag, ResultMessage.Success);
    }
}
