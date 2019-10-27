package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.mapper.VideoMapper;
import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.po.Video;
import com.chengxuxiaoba.video.model.po.VideoSummary;
import com.chengxuxiaoba.video.model.po.VideoWatchRecord;
import com.chengxuxiaoba.video.model.po.VideoWatchRecordCourseModuleStatistic;
import com.chengxuxiaoba.video.model.query.VideoQuery;
import com.chengxuxiaoba.video.model.query.VideoWatchRecordHistoryQuery;
import com.chengxuxiaoba.video.service.IBaseService;
import com.chengxuxiaoba.video.service.IVideoService;
import com.chengxuxiaoba.video.util.ListUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VideoService extends IBaseService<Video> implements IVideoService {

    @Value("${video.savepath}")
    private String videoUploadPath;

    @Value("#{'${video.suffexnamelimitation}'.split(',')}")
    private List<String> videoSuffexNameLimitation;

    @Autowired
    VideoMapper videoMapper;

    @Override
    public Boolean createNewVideo(Video video) {
        if (video == null)
            return false;

        Integer primaryKey = videoMapper.insert(video);
        return primaryKey > 0;
    }

    @Override
    public Video getSingle(Integer id) {
        if (id == null)
            return null;
        return videoMapper.getVideo(id);
    }

    @Override
    public Video getSingle(Integer courseModuleId, String name) {
        if (courseModuleId == null || StringUtil.isNullOrEmpty(name))
            return null;

        return videoMapper.getVideoByVideoName(courseModuleId, name);
    }

    @Override
    public List<Video> getVideoByCourseModuleId(Integer courseModuleId) {
        if (courseModuleId == null)
            return null;

        List<Video> retList = videoMapper.getVideoByCourseModuleId(courseModuleId);

        return retList;
    }

    @Override
    public PageResult<Video> getVideoByCourseModuleIdWithPage(Integer courseModuleId, PageInfo pageInfo) {
        if (courseModuleId == null || pageInfo == null)
            return null;

        VideoQuery query = new VideoQuery();
        query.setCourseModuleId(courseModuleId);
        query.setPageInfo(pageInfo);

        PageResult<Video> pageResult = super.getListByQuery(videoMapper, query);

        return pageResult;
    }

    @Override
    public Boolean uploadVideo(Video video) {
        Integer primaryKey = videoMapper.updateVideo(video);
        return primaryKey > 0;
    }

    @Override
    public VideoSummary getVideoSummary(Integer courseModuleId) {
        List<VideoSummary> videoSummaryList = getVideoSummary(new ArrayList<>(courseModuleId));

        return ListUtil.isNullOrEmpty(videoSummaryList) ? null : videoSummaryList.get(0);
    }

    @Override
    public List<VideoSummary> getVideoSummary(List<Integer> courseModuleIdList) {
        if (ListUtil.isNullOrEmpty(courseModuleIdList))
            return null;

        List<VideoSummary> videoSummaryList = videoMapper.getVideoSummary(courseModuleIdList);

        return videoSummaryList;
    }

    @Override
    public Boolean recordVideoWatching(Integer accountId, Integer courseModuleId, Integer videoId) {
        VideoWatchRecord videoWatchRecord = VideoWatchRecord.builder()
                .videoId(videoId)
                .accountId(accountId)
                .courseModuleId(courseModuleId)
                .build();

        videoMapper.insertWatchRecordHistory(videoWatchRecord);

        videoWatchRecord = videoMapper.getVideoWatchRecord(accountId, videoId);
        if (videoWatchRecord != null)
            videoMapper.updateWatchRecordTime(accountId, videoId);
        else {
            videoWatchRecord = new VideoWatchRecord();
            videoWatchRecord.setAccountId(accountId);
            videoWatchRecord.setVideoId(videoId);
            videoWatchRecord.setCourseModuleId(courseModuleId);
            Integer _primaryKey = videoMapper.insertWatchRecord(videoWatchRecord);

            return _primaryKey > 0;
        }
        return false;
    }

    @Override
    public Integer getVideoWatchCountInOneDay(Integer accountId, Date watchDay) {
        VideoWatchRecordHistoryQuery videoWatchRecordHistoryQuery = VideoWatchRecordHistoryQuery.builder()
                .accountId(accountId)
                .watchDay(watchDay == null ? (new Date()) : watchDay)
                .build();

        Integer count = videoMapper.getVideoWatchCount(videoWatchRecordHistoryQuery);
        return count;
    }

    @Override
    public List<VideoWatchRecordCourseModuleStatistic> getVideoWatchRecordCourseModuleStatistic(Integer accountId) {
        return videoMapper.getVideoWatchRecordCourseModuleStatistic(accountId);
    }

    @Override
    public List<Video> getVideoListHasBeenWatch(Integer accountId, Integer courseModuleId) {
        return videoMapper.getVideoListHasBeenWatch(accountId, courseModuleId);
    }


    @Override
    public List<Video> getVideoList(List<Integer> videoIdList) {
        if (ListUtil.isNullOrEmpty(videoIdList))
            return null;

        return videoMapper.getVideoList(videoIdList);
    }

    @Override
    public List<Video> getPreviousAndNextVideos(Integer videoId) {
        if (videoId == null || videoId == 0) {
            return null;
        }

        List<Video> videoList = videoMapper.getPreviousAndNextVideos(videoId);

        if(ListUtil.isNullOrEmpty(videoList))
        {
            return videoList;
        }


        if(videoList.size() == 1)
        {
            List<Video> resultList=new ArrayList<>();
            Video videoTemplate=videoList.get(0);

            if(videoTemplate.getId() > videoId)
            {
                resultList.add(null);
                resultList.add(videoTemplate);
            }
            else
            {
                resultList.add(videoTemplate);
                resultList.add(null);
            }
            return resultList;
        }

        return videoList;
    }
}
