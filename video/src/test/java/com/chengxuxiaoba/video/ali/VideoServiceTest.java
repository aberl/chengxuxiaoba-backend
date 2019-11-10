package com.chengxuxiaoba.video.ali;

import com.chengxuxiaoba.video.model.ali.VideoPlayAuth;
import com.chengxuxiaoba.video.model.ali.VideoPlayInfo;
import com.chengxuxiaoba.video.service.imp.ali.AliVideoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoServiceTest {

    @Autowired
    private AliVideoService videoService;

    private String videoId="37d21693e35a4c05873d25c230b14d4a";

    private String userId="userId_6";

    @Test
    public void getVideoPlayAuthTest()
    {
        VideoPlayAuth videoPlayAuth=videoService.getVideoPlayAuth(userId, videoId);

        System.out.println(videoPlayAuth);

        VideoPlayInfo videoPlayInfo = videoService.getVideoPlayInfo(userId, videoId);

        System.out.println(videoPlayInfo);
    }
}
