package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.model.Request.VO.VideoRequestVo;
import com.chengxuxiaoba.video.model.Response.VO.CourseModuleResponseVo;
import com.chengxuxiaoba.video.model.Result;
import com.chengxuxiaoba.video.model.ResultCode;
import com.chengxuxiaoba.video.model.ResultMessage;
import com.chengxuxiaoba.video.model.po.Video;
import com.chengxuxiaoba.video.service.IVideoService;
import com.chengxuxiaoba.video.service.IVoService;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/video")
public class VideoController {

    @Autowired
    private IVideoService videoService;

    @Autowired
    private IVoService voService;

    @PostMapping("/")
    public Result<Boolean> createVideo(@RequestBody VideoRequestVo requestBody) {
        Video video = voService.convertToVideo(requestBody);

        Boolean flag = videoService.createNewVideo(video);

        if(!flag)
            return new Result<Boolean>(ResultCode.Error, flag, ResultMessage.Fail);

        return new Result<Boolean>(ResultCode.Success, flag, ResultMessage.Success);
    }
}
