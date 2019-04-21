package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.UploadFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UploadFileMapper {
    Integer insertUploadFile(UploadFile uploadFile);

    List<UploadFile> getFileByName(@Param("nameList") List<String> nameList);

    Integer updateUploadFile(UploadFile uploadFile);
}
