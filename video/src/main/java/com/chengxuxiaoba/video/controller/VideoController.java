package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.Handler;
import com.chengxuxiaoba.video.model.*;
import com.chengxuxiaoba.video.model.Request.VO.VideoRequestVo;
import com.chengxuxiaoba.video.model.Response.VO.CourseModuleResponseVo;
import com.chengxuxiaoba.video.model.Response.VO.VideoResponseVo;
import com.chengxuxiaoba.video.model.po.Video;
import com.chengxuxiaoba.video.service.IVideoService;
import com.chengxuxiaoba.video.service.IVoService;
import com.chengxuxiaoba.video.util.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/videos")
public class VideoController {

    @Autowired
    private IVideoService videoService;

    @Autowired
    private IVoService voService;

    @PostMapping("/")
    public Result<Boolean> createVideo(VideoRequestVo requestBody) {
        Video video = voService.convertToVideo(requestBody);

        Boolean flag = videoService.createNewVideo(video);

        if (!flag)
            return new Result<Boolean>(ResultCode.Error, flag, ResultMessage.Fail);

        return new Result<Boolean>(ResultCode.Success, flag, ResultMessage.Success);
    }

    @GetMapping("/video/{id}")
    public Result<VideoResponseVo> getVideo(@PathVariable("id") Integer id) {
        Video video = videoService.getSingle(id);
        VideoResponseVo videoResponseVo = voService.convertToVideoResponseVo(video);

        return new Result<VideoResponseVo>(ResultCode.Success, videoResponseVo, ResultMessage.Success);
    }

    @GetMapping("/{courseModuleId}")
    public Result<PageResult<VideoResponseVo>> getVideoByCourseModuleId(@PathVariable("courseModuleId") Integer courseModuleId,
                                                                        @RequestParam("pagenum") Integer pageNum,
                                                                        @RequestParam(name = "pagesize", required = false) Integer pageSize,
                                                                        @RequestParam(name = "sort", required = false) String sort) {
        if (courseModuleId == null || courseModuleId == 0)
            return new Result<PageResult<VideoResponseVo>>(ResultCode.Error, null, ResultMessage.ParameterError);

        PageInfo pageInfo = new PageInfo();
        pageInfo.build(pageNum, pageSize, Handler.handleRestAPISort(sort), null);
        PageResult<Video> pageData = videoService.getVideoByCourseModuleIdWithPage(courseModuleId, pageInfo);

        List<VideoResponseVo> resultList = voService.convertToVideoResponseVo(pageData.getData());

        PageResult<VideoResponseVo> result = new PageResult<VideoResponseVo>(pageData.getCurrentNum(), pageData.getTotalCount(), resultList);

        return new Result<PageResult<VideoResponseVo>>(ResultCode.Success, result, ResultMessage.Success);
    }
}
