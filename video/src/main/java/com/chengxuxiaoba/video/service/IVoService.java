package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.Request.VO.VideoRequestVo;
import com.chengxuxiaoba.video.model.Response.VO.CourseModuleResponseVo;
import com.chengxuxiaoba.video.model.Response.VO.CourseResponseVo;
import com.chengxuxiaoba.video.model.Response.VO.VideoResponseVo;
import com.chengxuxiaoba.video.model.po.Course;
import com.chengxuxiaoba.video.model.po.CourseModule;
import com.chengxuxiaoba.video.model.po.Video;

import java.util.List;

public interface IVoService {
    CourseResponseVo convertToCourseResponseVo(Course course);

    List<CourseResponseVo> convertToCourseResponseVo(List<Course> course);

    CourseModuleResponseVo convertToCourseModuleResponseVo(CourseModule courseModule);

    List<CourseModuleResponseVo> convertToCourseModuleResponseVo(List<CourseModule> courseModuleList);

    Video convertToVideo(VideoRequestVo videoVo);

    VideoResponseVo convertToVideoResponseVo(Video video);

    List<VideoResponseVo> convertToVideoResponseVo(List<Video> videoList);
}
