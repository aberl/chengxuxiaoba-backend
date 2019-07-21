package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.po.Video;
import com.chengxuxiaoba.video.model.po.VideoSummary;
import com.chengxuxiaoba.video.model.po.VideoWatchRecord;
import com.chengxuxiaoba.video.model.po.VideoWatchRecordCourseModuleStatistic;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IVideoService {
    Boolean createNewVideo(Video video);

    Video getSingle(Integer id);

    Video getSingle(Integer courseModuleId, String name);

    List<Video> getVideoByCourseModuleId(Integer courseModuleId);

    PageResult<Video> getVideoByCourseModuleIdWithPage(Integer courseModuleId, PageInfo pageInfo);

    /**
     *
     *  * @param video
     * @return
     * @throws IOException
     */
    Boolean uploadVideo(Video video);

    VideoSummary getVideoSummary(Integer courseModuleId);

    List<VideoSummary> getVideoSummary(List<Integer> courseModuleIdList);

    Boolean recordVideoWatching(Integer accountId, Integer videoId);

    Boolean recordVideoWatching(Integer accountId, Integer courseModuleId, Integer videoId);

    List<VideoWatchRecord> getVideoWatchRecordList(Integer accountId,Integer courseModuleId);

    List<VideoWatchRecordCourseModuleStatistic> getVideoWatchRecordCourseModuleStatistic(Integer accountId);

    List<Video> getVideoListHasBeenWatch(Integer accountId,Integer courseModuleId);

    List<Video> getVideoList(List<Integer> videoIdList);
}
