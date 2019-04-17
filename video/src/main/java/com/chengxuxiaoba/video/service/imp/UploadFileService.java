package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.model.po.UploadFile;
import com.chengxuxiaoba.video.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadFileService implements IUploadFileService {
    @Value("${image.savepath}")
    private String imageUploadPath;

    @Value("#{'${image.suffexnamelimitation}'.split(',')}")
    private List<String> imageSuffexNameLimitation;

    @Value("${image.size}")
    private Integer imageMaxSize;


    @Value("${video.savepath}")
    private String videoUploadPath;

    @Value("#{'${video.suffexnamelimitation}'.split(',')}")
    private List<String> videoSuffexNameLimitation;

    @Value("${video.size}")
    private Integer videoMaxSize;
    @Override
    public UploadFile uploadFile(UploadFile uploadFile) {
        return null;
    }

    @Override
    public UploadFile getUploadFileByName(String name) {
        return null;
    }

    @Override
    public Boolean deleteUploadFile(String name) {
        return null;
    }

    @Override
    public UploadFile updateUploadFile(UploadFile uploadFile) {
        return null;
    }
}
