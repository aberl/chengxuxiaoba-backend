package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.*;
import com.chengxuxiaoba.video.model.query.VideoWatchRecordHistoryQuery;
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

    Integer insertWatchRecordHistory(VideoWatchRecord videoWatchRecord);

    Integer updateWatchRecordTime(@Param("accountId") Integer accountId,@Param("videoId")  Integer videoId);

    VideoWatchRecord getVideoWatchRecord(@Param("accountId") Integer accountId, @Param("videoId") Integer videoId);

    List<VideoWatchRecord> getVideoWatchRecordHistoryList(VideoWatchRecordHistoryQuery videoWatchRecordHistoryQuery);

    Integer getVideoWatchCount(VideoWatchRecordHistoryQuery videoWatchRecordHistoryQuery);

    List<VideoWatchRecordCourseModuleStatistic> getVideoWatchRecordCourseModuleStatistic(Integer accountId);

    List<Video> getVideoListHasBeenWatch(@Param("accountId") Integer accountId, @Param("courseModuleId") Integer courseModuleId);

    List<Video> getVideoList(@Param("videoIdList") List<Integer> videoIdList);

    List<Video> getPreviousAndNextVideos(@Param("videoId")  Integer videoId,@Param("courseModuleId")  Integer courseModuleId);
}
