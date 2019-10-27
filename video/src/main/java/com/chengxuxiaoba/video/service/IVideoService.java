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
import java.util.Date;
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

    Boolean recordVideoWatching(Integer accountId, Integer courseModuleId, Integer videoId);

    Integer getVideoWatchCountInOneDay(Integer accountId, Date watchDay);

    List<VideoWatchRecordCourseModuleStatistic> getVideoWatchRecordCourseModuleStatistic(Integer accountId);

    List<Video> getVideoListHasBeenWatch(Integer accountId,Integer courseModuleId);

    List<Video> getVideoList(List<Integer> videoIdList);

    /**
     * get previous one and next one, the result has 2 items, even contain null item if pre or next one is not exist
     * @param videoId
     * @return
     */
    List<Video> getPreviousAndNextVideos(Integer videoId);
}
