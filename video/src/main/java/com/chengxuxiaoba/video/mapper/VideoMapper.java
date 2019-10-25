package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.*;
import com.chengxuxiaoba.video.model.query.VideoUserWatchSummaryQuery;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoMapper extends BaseMapper<Video> {
    Integer insert(Video video);

    List<Video> getVideoByCourseModuleId(Integer courseModuleId);

    Video getVideo(Integer id);

    Video getVideoByVideoName(Integer courseModuleId, String name);

    Integer updateVideo(Video video);

    List<VideoSummary> getVideoSummary(@Param("courseModuleIdList") List<Integer> courseModuleIdList);

    Integer insertWatchRecord(VideoWatchRecord videoWatchRecord);

    Integer updateWatchRecordTime(@Param("accountId") Integer accountId,@Param("videoId")  Integer videoId);

    VideoWatchRecord getVideoWatchRecord(@Param("accountId") Integer accountId, @Param("videoId") Integer videoId);

    List<VideoWatchRecord> getVideoWatchRecordList(Integer accountId, Integer courseModuleId);

    List<VideoWatchRecord> getVideoWatchRecordListByAccountId(Integer accountId);

    List<VideoWatchRecordCourseModuleStatistic> getVideoWatchRecordCourseModuleStatistic(Integer accountId);

    List<Video> getVideoListHasBeenWatch(@Param("accountId") Integer accountId, @Param("courseModuleId") Integer courseModuleId);


    List<Video> getVideoList(@Param("videoIdList") List<Integer> videoIdList);

    /**
     * insert one video user Watch summary record
     * @param videoUserWatchSummary
     * @return
     */
    Integer insertVideoUserWatchSummary(VideoUserWatchSummary videoUserWatchSummary);

    /**
     *
     * @param accountId
     * @return
     */
    Integer addOneTimeVideoUserWatchSummary(Integer accountId);

    /**
     *
     * @param videoUserWatchSummaryQuery
     * @return
     */
    List<VideoUserWatchSummary> getVideoUserWatchSummaryList(VideoUserWatchSummaryQuery videoUserWatchSummaryQuery);
}
