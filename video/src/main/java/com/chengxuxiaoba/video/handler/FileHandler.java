package com.chengxuxiaoba.video.handler;

import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.ResultMessage;
import com.chengxuxiaoba.video.util.FileUtil;
import com.chengxuxiaoba.video.util.ListUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.List;

public class FileHandler {

    public static KeyValuePair<Boolean, String> uploadFile(MultipartFile multipartFile, String savePath, String name) throws IOException {
        return uploadFile(multipartFile, null, savePath, name);
    }

    public static KeyValuePair<Boolean, String> uploadFile(MultipartFile multipartFile, String savePath) throws IOException {
        return uploadFile(multipartFile, null, savePath, null);
    }

    public static KeyValuePair<Boolean, String> uploadFile(MultipartFile multipartFile, List<String> suffexNameLimitation, String savePath) throws IOException {
        return uploadFile(multipartFile, suffexNameLimitation, savePath, null);
    }

    public static KeyValuePair<Boolean, String> uploadFile(MultipartFile multipartFile, List<String> suffexNameLimitation, String savePath, String name) throws IOException {
        if (multipartFile == null)
            return new KeyValuePair(false, ResultMessage.FileIsNull);

        String suffixName = FileUtil.getSuffixName(multipartFile.getResource().getFilename());

        if (!ListUtil.isNullOrEmpty(suffexNameLimitation) && !suffexNameLimitation.contains(suffixName))
            return new KeyValuePair(false, ResultMessage.SuffexNameIsIllegal);

        name = StringUtil.isNullOrEmpty(name) ? String.format("%s.%s", UUID.randomUUID().toString(), suffixName) : name;

        String videoFileName = String.format("%s/%s", savePath, name);

        Boolean flag = FileUtil.uploadFile(multipartFile.getInputStream(), savePath, name);

        if (!flag)
            return new KeyValuePair(false, ResultMessage.Fail);

        return new KeyValuePair(true, videoFileName);
    }
}
