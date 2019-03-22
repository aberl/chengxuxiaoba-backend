package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.Video;

public interface VideoMapper {
    Integer insert(Video video);

    Video getVideoByCourseModuleId(Integer courseModuleId);

    Video getVideo(Integer id);

    Integer updateVideo(Video video);
}
