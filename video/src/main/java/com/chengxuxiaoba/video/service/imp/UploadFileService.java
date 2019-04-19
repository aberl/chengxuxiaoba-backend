package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.constant.CommonStatus;
import com.chengxuxiaoba.video.handler.FileHandler;
import com.chengxuxiaoba.video.mapper.UploadFileMapper;
import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.Response.VO.UploadFileResponseVo;
import com.chengxuxiaoba.video.model.Result;
import com.chengxuxiaoba.video.model.ResultCode;
import com.chengxuxiaoba.video.model.ResultMessage;
import com.chengxuxiaoba.video.model.po.UploadFile;
import com.chengxuxiaoba.video.service.IUploadFileService;
import com.chengxuxiaoba.video.util.FileUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class UploadFileService implements IUploadFileService {
    @Value("${uploadfile.savepath}")
    private String uploadFilePath;

    @Value("${uploadfile.size}")
    private String uploadFileMaxSize;

    @Autowired
    private UploadFileMapper uploadFileMapper;

    @Override
    public UploadFile uploadFile(MultipartFile multipartFile, String purpose) throws IOException {
        if (multipartFile == null || StringUtil.isNullOrEmpty(purpose))
            return null;

        String suffixName = FileUtil.getSuffixName(multipartFile.getResource().getFilename());

        Long size = multipartFile.getSize();

        String name = generateFileName(suffixName);

        String filePath = generateFilePath(purpose);

        KeyValuePair<Boolean, String> kvp = FileHandler.uploadFile(multipartFile, filePath, name);
        if (!kvp.getKey())
            return null;

        String saveFilePath = kvp.getValue();
        UploadFile uploadFile = new UploadFile();

        uploadFile.setName(name);
        uploadFile.setPurpose(purpose);
        uploadFile.setSize(size);
        uploadFile.setPath(saveFilePath);

        return uploadFile;
    }

    @Override
    public Boolean uploadFile(UploadFile uploadFile) {
        if (uploadFile == null)
            return false;

        Integer primaryKey = uploadFileMapper.insertUploadFile(uploadFile);
        return primaryKey > 0;
    }

    @Override
    public UploadFile getUploadFileByName(String name) {
        return null;
    }

    @Override
    public Boolean deleteUploadFile(String fileName) {

        UploadFile uploadFile =  getUploadFileByName(fileName);

        if(uploadFile == null)
        {
            return false;
        }

        String savePath=uploadFile.getPath();
        uploadFile.setStatus(CommonStatus.INACTIVE.getValue());
        uploadFile.setUpdateDateTime(new Date());
        updateUploadFile(uploadFile);

        FileUtil.deleteFile(fileName);

        return true;
    }

    @Override
    public UploadFile updateUploadFile(UploadFile uploadFile) {
        if(uploadFile == null)
        return null;

        uploadFileMapper.updateMessage(uploadFile);
        return uploadFile;
    }

    @Override
    public String generateFileName(String suffixName) {
        String name = String.format("%s.%s", UUID.randomUUID().toString(), suffixName);
        return name;
    }

    @Override
    public String generateFilePath(String purpose) {
        String filePath = String.format("%s/%s", uploadFilePath, purpose);

        return filePath;
    }
}
