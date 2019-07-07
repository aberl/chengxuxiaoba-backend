package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.Video;
import com.chengxuxiaoba.video.model.po.VideoSummary;
import com.chengxuxiaoba.video.model.po.VideoWatchRecord;
import com.chengxuxiaoba.video.model.po.VideoWatchRecordCourseModuleStatistic;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoMapper extends  BaseMapper<Video>{
    Integer insert(Video video);

    List<Video> getVideoByCourseModuleId(Integer courseModuleId);

    Video getVideo(Integer id);

    Video getVideoByVideoName(Integer courseModuleId, String name);

    Integer updateVideo(Video video);

    List<VideoSummary> getVideoSummary(@Param("courseModuleIdList") List<Integer> courseModuleIdList);

    Integer insertWatchRecord(VideoWatchRecord videoWatchRecord);

    Integer updateWatchRecordTime(Integer accountId, Integer videoId);

    VideoWatchRecord getVideoWatchRecord(Integer accountId, Integer videoId);

    List<VideoWatchRecord> getVideoWatchRecordList(Integer accountId,Integer courseModuleId);

    List<VideoWatchRecord> getVideoWatchRecordListByAccountId(Integer accountId);

    List<VideoWatchRecordCourseModuleStatistic> getVideoWatchRecordCourseModuleStatistic(Integer accountId);
}
