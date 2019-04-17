package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.constant.FilePurpose;
import com.chengxuxiaoba.video.model.Request.VO.UploadFileRequestVo;
import com.chengxuxiaoba.video.model.Response.VO.UploadFileResponseVo;
import com.chengxuxiaoba.video.model.Result;
import com.chengxuxiaoba.video.model.ResultCode;
import com.chengxuxiaoba.video.model.ResultMessage;
import com.chengxuxiaoba.video.util.StringUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/UploadFile")
public class FileController {
    //upload images
    //upload video

    //name:guid
    //purpose
    //path
    //sizelimit
    //categorylimit

    @PostMapping("/videos")
    public Result<UploadFileResponseVo> createNewUploadFile(UploadFileRequestVo uploadFileRequestVo)
    {
        if(!FilePurpose.getAllPurposeList().contains(uploadFileRequestVo.getPurpose()))
    return new Result<UploadFileResponseVo>(ResultCode.Error, null, ResultMessage.uploadFileHasNoPurpose);

        if(uploadFileRequestVo.getUploadFile() == null)
        return new Result<UploadFileResponseVo>(ResultCode.Error, null, ResultMessage.FileIsNull);

    }

}
