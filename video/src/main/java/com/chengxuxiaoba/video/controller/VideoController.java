package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.annotation.AuthorizationValidation;
import com.chengxuxiaoba.video.handler.Handler;
import com.chengxuxiaoba.video.model.*;
import com.chengxuxiaoba.video.model.Request.VO.VideoRequestVo;
import com.chengxuxiaoba.video.model.Response.VO.AliVideoInfoResponseVo;
import com.chengxuxiaoba.video.model.Response.VO.VideoResponseVo;
import com.chengxuxiaoba.video.model.Response.VO.VideoWatchRecordCourseModuleStatisticResponseVo;
import com.chengxuxiaoba.video.model.po.Video;
import com.chengxuxiaoba.video.model.po.VideoWatchRecord;
import com.chengxuxiaoba.video.model.po.VideoWatchRecordCourseModuleStatistic;
import com.chengxuxiaoba.video.service.IAuthenticationService;
import com.chengxuxiaoba.video.service.IVideoService;
import com.chengxuxiaoba.video.service.IVoService;
import com.chengxuxiaoba.video.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class VideoController extends BaseController {

    @Autowired
    private IVideoService videoService;

    @Autowired
    private IVoService voService;

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/videos")
    @AuthorizationValidation()
    public Result<Boolean> createVideo(@RequestBody VideoRequestVo requestBody) throws IOException {
        if (videoService.getSingle(requestBody.getCourseModuleId(), requestBody.getName()) != null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.SameVideoNameInCurrentCourseModule);

        Video video = voService.convertToVideo(requestBody);

        Boolean flag = videoService.createNewVideo(video);

        if (!flag)
            return new Result<Boolean>(ResultCode.Error, flag, ResultMessage.Fail);

        return new Result<Boolean>(ResultCode.Success, flag, ResultMessage.Success);
    }

    @PostMapping("/videos/record")
    @AuthorizationValidation()
    public Result<Boolean> watchVideo(@RequestBody VideoRequestVo requestBody) throws IOException {
        if (requestBody.getId() == null || requestBody.getId() == 0)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.ParameterError);

        CurrentLoginUserModel currentLoginUserModel = authenticationService.getCurrentLoginUserModelFromRequest();

        Integer accountId = currentLoginUserModel.getUserId();

        if (accountId == null || accountId == 0)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.ParameterError);

        Video video = videoService.getSingle(requestBody.getId());
        Integer viewCount = video.getViewCount() == null ? 0 : video.getViewCount();

        video.setViewCount(viewCount + 1);

        videoService.recordVideoWatching(accountId, video.getCourseModuleId(), requestBody.getId());

        Boolean flag = videoService.uploadVideo(video);

        if (!flag)
            return new Result<Boolean>(ResultCode.Error, flag, ResultMessage.Fail);

        return new Result<Boolean>(ResultCode.Success, flag, ResultMessage.Success);
    }

    @PutMapping("/videos")
    @AuthorizationValidation()
    public Result<Boolean> updateVideo(@RequestBody VideoRequestVo requestBody) throws IOException {
        Video video = videoService.getSingle(requestBody.getId());
        if (video == null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.VideoIsNotExist);

        video = voService.convertToVideo(requestBody);

        Boolean flag = videoService.uploadVideo(video);

        if (!flag)
            return new Result<Boolean>(ResultCode.Error, flag, ResultMessage.Fail);

        return new Result<Boolean>(ResultCode.Success, flag, ResultMessage.Success);
    }

    @GetMapping("/videos/{id}")
    public Result<VideoResponseVo> getVideo(@PathVariable("id") Integer id) {
        Video video = videoService.getSingle(id);

        VideoResponseVo videoResponseVo = voService.convertToVideoResponseVo(video);

        return new Result<VideoResponseVo>(ResultCode.Success, videoResponseVo, ResultMessage.Success);
    }

    @GetMapping("/videos/ali/{alivid}")
    @AuthorizationValidation()
    public Result<AliVideoInfoResponseVo> getAliVideo(@PathVariable("alivid") String alivid) {
        CurrentLoginUserModel currentLoginUserModel = authenticationService.getCurrentLoginUserModelFromRequest();

        AliVideoInfoResponseVo aliVideoInfoResponseVo = voService.getAliVideoInfo(currentLoginUserModel.getUserId(), alivid);

        return new Result<AliVideoInfoResponseVo>(ResultCode.Success, aliVideoInfoResponseVo, ResultMessage.Success);
    }

    @GetMapping("/courses/{courseModuleId}/videos")
    public Result<PageResult<VideoResponseVo>> getVideoListByCourseModuleId(@PathVariable("courseModuleId") Integer courseModuleId,
                                                                            @RequestParam("pagenum") Integer pageNum,
                                                                            @RequestParam(name = "pagesize", required = false) Integer pageSize,
                                                                            @RequestParam(name = "sort", required = false) String sort) {
        if (courseModuleId == null || courseModuleId == 0)
            return new Result<PageResult<VideoResponseVo>>(ResultCode.Error, null, ResultMessage.ParameterError);

        PageInfo pageInfo = super.generatePageInfo(pageNum, pageSize, sort);

        PageResult<Video> pageData = videoService.getVideoByCourseModuleIdWithPage(courseModuleId, pageInfo);

        List<VideoResponseVo> resultList = voService.convertToVideoResponseVo(pageData.getData());

        PageResult<VideoResponseVo> result = new PageResult<VideoResponseVo>(pageData.getCurrentNum(), pageData.getTotalCount(), resultList);

        return new Result<PageResult<VideoResponseVo>>(ResultCode.Success, result, ResultMessage.Success);
    }

    @GetMapping("/videos/recordstatistic")
    @AuthorizationValidation()
    public Result<List<VideoWatchRecordCourseModuleStatisticResponseVo>> getWatchingRecordStatistic() {

        CurrentLoginUserModel currentLoginUserModel = authenticationService.getCurrentLoginUserModelFromRequest();

        List<VideoWatchRecordCourseModuleStatistic> recordStatisticList = videoService
                .getVideoWatchRecordCourseModuleStatistic(currentLoginUserModel.getUserId());

        List<VideoWatchRecordCourseModuleStatisticResponseVo> responseVoResult = voService.convertToVideoWatchRecordCourseModuleStatisticResponseVo(recordStatisticList);

        return new Result<List<VideoWatchRecordCourseModuleStatisticResponseVo>>(ResultCode.Success, responseVoResult, ResultMessage.Success);
    }

    @GetMapping("/videos/record/{coursemoduleid}")
    @AuthorizationValidation()
    public Result<List<VideoResponseVo>> getWatchingRecordList(@PathVariable("coursemoduleid") Integer coursemoduleid) {

        CurrentLoginUserModel currentLoginUserModel = authenticationService.getCurrentLoginUserModelFromRequest();

        List<Video> recordList = videoService.getVideoListHasBeenWatch(currentLoginUserModel.getUserId(), coursemoduleid);

        if (ListUtil.isNullOrEmpty(recordList))
            return new Result<List<VideoResponseVo>>(ResultCode.Success, null, ResultMessage.Success);

        List<VideoResponseVo> videoResponseVoList = voService.convertToVideoResponseVo(recordList, false);

        return new Result<List<VideoResponseVo>>(ResultCode.Success, videoResponseVoList, ResultMessage.Success);
    }
}
