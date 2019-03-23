package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.Video;
import com.github.pagehelper.Page;

import java.util.List;

public interface VideoMapper {
    Integer insert(Video video);

    List<Video> getVideoByCourseModuleId(Integer courseModuleId);

    Page<Video> getVideoByCourseModuleIdWithPage(Integer courseModuleId);

    Video getVideo(Integer id);

    Integer updateVideo(Video video);
}
