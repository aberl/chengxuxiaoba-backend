package com.chengxuxiaoba.video.ali;

import com.chengxuxiaoba.video.model.ali.ImageInfo;
import com.chengxuxiaoba.video.service.imp.ali.AliImageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageServiceTest {
    private String videoId = "0c00ec720d084692b7e5364424de49d5";
    private String videoId2 = "6df0d37c343d4f1784ad6f4a3a5722e7";

    @Autowired
    private AliImageService aliImageService;

    @Test
    public void getImageInfoTest() {
        ImageInfo imageInfo = aliImageService.getImageInfo(videoId);

        System.out.println(imageInfo);
    }

    @Test
    public void getImageInfoListTest() {
        List<ImageInfo> imageInfos = aliImageService.getImageInfoList(Arrays.asList(videoId2,videoId));

        System.out.println(imageInfos);
    }
}
