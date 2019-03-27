package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.Video;
import com.github.pagehelper.Page;

import java.util.List;

public interface VideoMapper extends  BaseMapper<Video>{
    Integer insert(Video video);

    List<Video> getVideoByCourseModuleId(Integer courseModuleId);

    Video getVideo(Integer id);

    Video getVideoByVideoName(Integer courseModuleId, String name);

    Integer updateVideo(Video video);
}
