package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.po.Video;
import com.github.pagehelper.Page;

import java.util.List;

public interface IVideoService {
    Boolean createNewVideo(Video video);

    Video getSingle(Integer id);

    List<Video> getVideoByCourseModuleId(Integer courseModuleId);

    PageResult<Video> getVideoByCourseModuleIdWithPage(Integer courseModuleId, PageInfo pageInfo);
}
