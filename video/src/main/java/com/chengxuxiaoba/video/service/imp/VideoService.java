package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.mapper.VideoMapper;
import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.ResultMessage;
import com.chengxuxiaoba.video.model.po.Video;
import com.chengxuxiaoba.video.service.IVideoService;
import com.chengxuxiaoba.video.util.FileUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class VideoService implements IVideoService {

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
        return videoMapper.getVideo(id);
    }

    @Override
    public Video getSingle(Integer courseModuleId, String name) {
        if(courseModuleId == null || StringUtil.isNullOrEmpty(name))
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

        PageHelper.startPage(pageInfo.getCurrentPageNum() - 1, pageInfo.getPageSize());

        PageHelper.orderBy(pageInfo.getSort());

        Page<Video> retList = videoMapper.getVideoByCourseModuleIdWithPage(courseModuleId);

        PageResult<Video> pageResult = new PageResult<Video>(retList.getPageNum(), retList.getTotal(), retList.getResult());

        return pageResult;
    }

    @Override
    public KeyValuePair<Boolean, String> uploadVideo(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null)
            return new KeyValuePair(false, ResultMessage.FileIsNull);

        String suffixName=FileUtil.getSuffixName(multipartFile.getResource().getFilename());

        if(!videoSuffexNameLimitation.contains(suffixName))
            return new KeyValuePair(false, ResultMessage.SuffexNameIsIllegal);

        String videoName = String.format("%s.%s", UUID.randomUUID().toString(), suffixName);

        String videoFileName = String.format("%s/%s", videoUploadPath, videoName);

        Boolean flag = FileUtil.uploadFile(multipartFile.getInputStream(), videoUploadPath, videoName);

        if (!flag)
            return new KeyValuePair(false, ResultMessage.Fail);

        return new KeyValuePair(true, videoFileName);
    }

}
