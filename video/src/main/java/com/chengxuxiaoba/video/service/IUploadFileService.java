package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.po.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUploadFileService {
    UploadFile uploadFile(MultipartFile file, String purpose) throws IOException;

    Boolean uploadFile(UploadFile uploadFile);

    UploadFile getUploadFileByName(String name);

    Boolean deleteUploadFile(String fileName);

    Integer updateUploadFile(UploadFile uploadFile);

    String generateFileName(String suffixName);

    String generateFilePath(String purpose);
}
