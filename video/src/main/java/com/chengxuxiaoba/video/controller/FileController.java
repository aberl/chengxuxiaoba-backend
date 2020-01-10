package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.annotation.AuthorizationValidation;
import com.chengxuxiaoba.video.constant.CommonStatus;
import com.chengxuxiaoba.video.constant.FilePurpose;
import com.chengxuxiaoba.video.constant.RoleConstant;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RestController
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

    @PostMapping("/uploadfile/file")
    @AuthorizationValidation(role = RoleConstant.ADMIN)
    public Result<UploadFileResponseVo> createNewUploadFile(UploadFileRequestVo uploadFileRequestVo) throws IOException {
        if (!FilePurpose.getAllPurposeList().contains(uploadFileRequestVo.getPurpose()))
            return new Result<UploadFileResponseVo>(ResultCode.Error, null, ResultMessage.uploadFileHasNoPurpose);

        if (uploadFileRequestVo.getUploadFile() == null)
            return new Result<UploadFileResponseVo>(ResultCode.Error, null, ResultMessage.FileIsNull);

        UploadFile uploadFile = uploadFileService.uploadFile(uploadFileRequestVo.getUploadFile(), uploadFileRequestVo.getPurpose());

        Boolean flag = uploadFileService.uploadFile(uploadFile);

        if (!flag)
            return new Result<UploadFileResponseVo>(ResultCode.Error, null, ResultMessage.Fail);

        UploadFileResponseVo uploadFileResponseVo = voService.convertToUploadFileResponseVo(uploadFile);

        return new Result<UploadFileResponseVo>(ResultCode.Success, uploadFileResponseVo, ResultMessage.Success);
    }

    @DeleteMapping("/uploadfile/{name}")
    @AuthorizationValidation(role = RoleConstant.ADMIN)
    public Result<Boolean> deleteUploadFile(@PathVariable("name") String name) throws IOException {
        if (StringUtil.isNullOrEmpty(name))
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.NameCannotBeNull);

        Boolean flag = uploadFileService.deleteUploadFile(name);

        if (!flag)
            return new Result<Boolean>(ResultCode.Error, flag, ResultMessage.Fail);

        return new Result<Boolean>(ResultCode.Success, true, ResultMessage.Success);
    }

    @GetMapping("/file/{name}")
    @AuthorizationValidation()
    public Result<Boolean> download(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtil.isNullOrEmpty(name)) {
            return new Result<>(ResultCode.Error, false, ResultMessage.ParameterError);
        }

        UploadFile uploadFile = uploadFileService.getUploadFileByName(name);

        if (uploadFile == null) {
            return new Result<>(ResultCode.Error, false, ResultMessage.FileIsNull);
        }

        String path = uploadFile.getPath();

        FileUtil.download(path, response);

        return new Result<>(ResultCode.Success, true, ResultMessage.Success);
    }
}
