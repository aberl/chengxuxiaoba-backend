package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.mapper.VideoMapper;
import com.chengxuxiaoba.video.model.po.Video;
import com.chengxuxiaoba.video.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
