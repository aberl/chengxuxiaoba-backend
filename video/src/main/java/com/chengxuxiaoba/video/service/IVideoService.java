package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.po.Video;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IVideoService {
    Boolean createNewVideo(Video video);

    Video getSingle(Integer id);

    List<Video> getVideoByCourseModuleId(Integer courseModuleId);

    PageResult<Video> getVideoByCourseModuleIdWithPage(Integer courseModuleId, PageInfo pageInfo);

    /**
     * upload file, and return a KeyValuePair object, key is upload flag, value is message when upload success then message is upload file path and name
     * @param file
     * @return
     * @throws IOException
     */
    KeyValuePair<Boolean, String> uploadVideo(MultipartFile multipartFile) throws IOException;
}
