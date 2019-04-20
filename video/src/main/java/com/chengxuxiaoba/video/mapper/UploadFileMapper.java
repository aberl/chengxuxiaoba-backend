package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.UploadFile;

public interface UploadFileMapper {
    Integer insertUploadFile(UploadFile uploadFile);

    UploadFile getFileByName(String name);

    Integer updateUploadFile(UploadFile uploadFile);
}
