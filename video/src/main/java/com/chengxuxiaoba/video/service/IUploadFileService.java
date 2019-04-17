package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.po.UploadFile;

public interface IUploadFileService {

    UploadFile uploadFile(UploadFile uploadFile);

    UploadFile getUploadFileByName(String name);

    Boolean deleteUploadFile(String name);

    UploadFile updateUploadFile(UploadFile uploadFile);
}
