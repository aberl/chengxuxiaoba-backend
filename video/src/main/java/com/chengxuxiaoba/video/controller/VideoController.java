package com.chengxuxiaoba.video.controller;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.kms.model.v20160120.GenerateDataKeyResponse;
import com.aliyuncs.vod.model.v20170321.SubmitTranscodeJobsResponse;
import com.chengxuxiaoba.video.annotation.AuthorizationValidation;
import com.chengxuxiaoba.video.annotation.VideoWatchingPermissionValidation;
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
import com.chengxuxiaoba.video.service.imp.ali.MediaService;
import com.chengxuxiaoba.video.util.JSONUtil;
import com.chengxuxiaoba.video.util.ListUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class VideoController extends BaseController {

    @Autowired
    private IVideoService videoService;

    @Autowired
    private IVoService voService;

    @Autowired
    private IAuthenticationService authenticationService;

    @Autowired
    private MediaService mediaService;

    @PostMapping("/videos")
    @AuthorizationValidation()
    public Result<Boolean> createVideo(@RequestBody VideoRequestVo requestBody) throws Exception {
        if (videoService.getSingle(requestBody.getCourseModuleId(), requestBody.getName()) != null)
            return new Result<>(ResultCode.Error, false, ResultMessage.SameVideoNameInCurrentCourseModule);

        Video video = voService.convertToVideo(requestBody);

        Boolean flag = videoService.createNewVideo(video);

        if (!flag)
            return new Result<>(ResultCode.Error, flag, ResultMessage.Fail);

        return new Result<>(ResultCode.Success, flag, ResultMessage.Success);
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
            return new Result<>(ResultCode.Error, flag, ResultMessage.Fail);

        return new Result<>(ResultCode.Success, flag, ResultMessage.Success);
    }

    @PostMapping("/videos/ali/encode")
    @AuthorizationValidation()
    public Result<Boolean> encodeVideo(@RequestBody VideoRequestVo requestBody) {
        if (StringUtil.isNullOrEmpty(requestBody.getAliVideoId()))
            return new Result<>(ResultCode.Error, false, ResultMessage.ParameterError);

        try {
            GenerateDataKeyResponse generateDataKeyResponse = mediaService.generateDataKey(null);
            String generateDataKeyResponseJson = JSONUtil.serialize(generateDataKeyResponse);
            System.out.println(generateDataKeyResponseJson);

            SubmitTranscodeJobsResponse submitTranscodeJobsResponse = mediaService.submitTranscodeJobs(requestBody.getAliVideoId(),
                    generateDataKeyResponse.getCiphertextBlob());

            String submitTranscodeJobsResponseJson = JSONUtil.serialize(submitTranscodeJobsResponse);

            log.info("encode Video finished, and ali video id is {}, response data is {}",
                    requestBody.getAliVideoId(), submitTranscodeJobsResponseJson);

            return new Result<>(ResultCode.Success, true, ResultMessage.Success);
        } catch (Exception ex) {
            log.error("encode ali video occur exception, ali video id is {}, and exception is {}",requestBody.getAliVideoId(),ex);
            return new Result<>(ResultCode.Error, false, ResultMessage.Fail);
        }
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
    @VideoWatchingPermissionValidation()
    public Result<AliVideoInfoResponseVo> getAliVideo(@PathVariable("alivid") String alivid) {
        CurrentLoginUserModel currentLoginUserModel = authenticationService.getCurrentLoginUserModelFromRequest();

        AliVideoInfoResponseVo aliVideoInfoResponseVo = voService.getAliVideoInfo(currentLoginUserModel.getUserId(), alivid);

        return new Result<>(ResultCode.Success, aliVideoInfoResponseVo, ResultMessage.Success);
    }

    @GetMapping("/courses/{courseModuleId}/videos")
    public Result<PageResult<VideoResponseVo>> getVideoListByCourseModuleId(@PathVariable("courseModuleId") Integer courseModuleId,
                                                                            @RequestParam("pagenum") Integer pageNum,
                                                                            @RequestParam(name = "pagesize", required = false) Integer pageSize,
                                                                            @RequestParam(name = "sort", required = false) String sort) {
        if (courseModuleId == null || courseModuleId == 0)
            return new Result<>(ResultCode.Error, null, ResultMessage.ParameterError);

        PageInfo pageInfo = super.generatePageInfo(pageNum, pageSize, sort);

        PageResult<Video> pageData = videoService.getVideoByCourseModuleIdWithPage(courseModuleId, pageInfo);

        List<VideoResponseVo> resultList = voService.convertToVideoResponseVo(pageData.getData());

        PageResult<VideoResponseVo> result = new PageResult<VideoResponseVo>(pageData.getCurrentNum(), pageData.getTotalCount(), resultList);

        return new Result<>(ResultCode.Success, result, ResultMessage.Success);
    }

    @GetMapping("/videos/recordstatistic")
    @AuthorizationValidation()
    public Result<List<VideoWatchRecordCourseModuleStatisticResponseVo>> getWatchingRecordStatistic() {

        CurrentLoginUserModel currentLoginUserModel = authenticationService.getCurrentLoginUserModelFromRequest();

        List<VideoWatchRecordCourseModuleStatistic> recordStatisticList = videoService
                .getVideoWatchRecordCourseModuleStatistic(currentLoginUserModel.getUserId());

        List<VideoWatchRecordCourseModuleStatisticResponseVo> responseVoResult = voService.convertToVideoWatchRecordCourseModuleStatisticResponseVo(recordStatisticList);

        return new Result<>(ResultCode.Success, responseVoResult, ResultMessage.Success);
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

    @GetMapping("/videos/{id}/preandnext")
    public Result<List<VideoResponseVo>> getPreAndNextVideoList(@PathVariable("id") Integer id) {
        List<Video> videoList = videoService.getPreviousAndNextVideos(id);

        List<VideoResponseVo> responseVoList = null;

        if (ListUtil.isNotNullOrEmpty(videoList)) {
            responseVoList = new ArrayList<>();
            Video previousOne = videoList.get(0);
            Video nextOne = videoList.get(1);

            responseVoList.add(voService.convertToVideoResponseVo(previousOne));
            responseVoList.add(voService.convertToVideoResponseVo(nextOne));
        }

        return new Result<List<VideoResponseVo>>(ResultCode.Success, responseVoList, ResultMessage.Success);
    }
}
