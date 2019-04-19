package com.chengxuxiaoba.video.model.Request.VO;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.Date;

public class UploadFileRequestVo {
    private String name;
    private String purpose;
    private MultipartFile uploadFile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public MultipartFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(MultipartFile uploadFile) {
        this.uploadFile = uploadFile;
    }
}
