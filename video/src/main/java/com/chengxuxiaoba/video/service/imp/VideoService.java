package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.Handler;
import com.chengxuxiaoba.video.mapper.VideoMapper;
import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.po.Video;
import com.chengxuxiaoba.video.service.IVideoService;
import com.chengxuxiaoba.video.util.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService implements IVideoService {
    @Autowired
    VideoMapper videoMapper;

    @Override
    public Boolean createNewVideo(Video video) {
        if(video == null)
            return  false;

        Integer primaryKey=videoMapper.insert(video);
        return primaryKey>0;
    }

    @Override
    public List<Video> getVideoByCourseModuleId(Integer courseModuleId) {
       if(courseModuleId == null)
           return  null;

        List<Video> retList=videoMapper.getVideoByCourseModuleId(courseModuleId);

        return retList;
    }

    @Override
    public PageResult<Video> getVideoByCourseModuleIdWithPage(Integer courseModuleId, PageInfo pageInfo) {
        if(courseModuleId == null || pageInfo== null)
            return  null;

        PageHelper.startPage(pageInfo.getCurrentPageNum()-1,pageInfo.getPageSize());

        PageHelper.orderBy(pageInfo.getSort());

        Page<Video> retList=videoMapper.getVideoByCourseModuleIdWithPage(courseModuleId);

        PageResult<Video> pageResult=new PageResult<Video>(retList.getPageNum(), retList.getTotal(), retList.getResult());

        return pageResult;
    }

}
