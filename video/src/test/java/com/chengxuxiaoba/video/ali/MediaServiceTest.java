package com.chengxuxiaoba.video.ali;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.kms.model.v20160120.GenerateDataKeyResponse;
import com.aliyuncs.vod.model.v20170321.SubmitTranscodeJobsResponse;
import com.chengxuxiaoba.video.service.imp.ali.MediaService;
import com.chengxuxiaoba.video.util.JSONUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MediaServiceTest {
    private String videoId = "322721094fb44052914fa7a5bc163b31";

    @Autowired
    private MediaService mediaService;

    @Test
    public void generateDataKeyTest() throws ClientException {
        GenerateDataKeyResponse generateDataKeyResponse = mediaService.generateDataKey(null);
        String json = JSONUtil.serialize(generateDataKeyResponse);
        System.out.println(json);
    }

    @Test
    public void submitTranscodeJobsTest() throws Exception {
        GenerateDataKeyResponse generateDataKeyResponse = mediaService.generateDataKey(null);
        String generateDataKeyResponseJson = JSONUtil.serialize(generateDataKeyResponse);
        System.out.println(generateDataKeyResponseJson);

        SubmitTranscodeJobsResponse submitTranscodeJobsResponse = mediaService.submitTranscodeJobs(videoId, generateDataKeyResponse.getCiphertextBlob());
        String submitTranscodeJobsResponseJson = JSONUtil.serialize(submitTranscodeJobsResponse);
        System.out.println(submitTranscodeJobsResponseJson);
    }
}
