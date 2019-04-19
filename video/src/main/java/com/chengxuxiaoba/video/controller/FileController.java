package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.constant.CommonStatus;
import com.chengxuxiaoba.video.constant.FilePurpose;
import com.chengxuxiaoba.video.model.Request.VO.UploadFileRequestVo;
import com.chengxuxiaoba.video.model.Response.VO.UploadFileResponseVo;
import com.chengxuxiaoba.video.model.Result;
import com.chengxuxiaoba.video.model.ResultCode;
import com.chengxuxiaoba.video.model.ResultMessage;
import com.chengxuxiaoba.video.model.po.UploadFile;
import com.chengxuxiaoba.video.service.IUploadFileService;
import com.chengxuxiaoba.video.service.IVoService;
import com.chengxuxiaoba.video.util.FileUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping(value = "/uploadfile")
public class FileController {
    //upload images
    //upload video

    //name:guid
    //purpose
    //path
    //sizelimit
    //categorylimit
    @Autowired
    private IVoService voService;

    @Autowired
    private IUploadFileService uploadFileService;

    @PostMapping("/file")
    public Result<Boolean> createNewUploadFile(UploadFileRequestVo uploadFileRequestVo)  throws IOException {
        if (!FilePurpose.getAllPurposeList().contains(uploadFileRequestVo.getPurpose()))
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.uploadFileHasNoPurpose);

        if (uploadFileRequestVo.getUploadFile() == null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.FileIsNull);

        UploadFile uploadFile =uploadFileService.uploadFile(uploadFileRequestVo.getUploadFile(),uploadFileRequestVo.getPurpose());

        Boolean flag = uploadFileService.uploadFile(uploadFile);

        return new Result<Boolean>(ResultCode.Success, true, ResultMessage.Success);
    }

    @DeleteMapping("/{name}")
    public Result<Boolean> createNewUploadFile(@PathVariable("name")String name)  throws IOException {
        if (StringUtil.isNullOrEmpty(name))
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.NameCannotBeNull);

        uploadFileService.deleteUploadFile(name);

        return new Result<Boolean>(ResultCode.Success, true, ResultMessage.Success);
    }

}
