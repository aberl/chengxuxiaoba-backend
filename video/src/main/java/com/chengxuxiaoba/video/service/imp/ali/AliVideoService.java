package com.chengxuxiaoba.video.service.imp.ali;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.chengxuxiaoba.video.model.ali.Credential;
import com.chengxuxiaoba.video.model.ali.VideoPlayAuth;
import com.chengxuxiaoba.video.model.ali.VideoPlayInfo;
import com.chengxuxiaoba.video.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AliVideoService {
    @Autowired
    private AliCredentialService credentialService;

    @Autowired
    private AliClientService aliClientService;

    /*获取播放地址函数*/
    private GetPlayInfoResponse getVideoPlayInfoResponse(DefaultAcsClient client, String videoId) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }

    /*获取播放凭证函数*/
    private GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client, String videoId) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }

    public VideoPlayInfo getVideoPlayInfo(String userId, String videoId) {
        try {
            Credential credential = credentialService.generateCredential(userId);
            DefaultAcsClient client = aliClientService.initVodClient(credential.getAccessKeyId(),
                    credential.getAssessKeySecret(),
                    credential.getSecrurityToken());

            GetPlayInfoResponse response = getVideoPlayInfoResponse(client, videoId);

            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

            if (ListUtil.isNullOrEmpty(playInfoList)) {
                return null;
            }

            GetPlayInfoResponse.PlayInfo playInfo = playInfoList.get(0);

            VideoPlayInfo videoPlayInfo = VideoPlayInfo.builder()
                    .baseTitle(response.getVideoBase().getTitle())
                    .playURL(playInfo.getPlayURL())
                    .build();

            return videoPlayInfo;
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return null;
    }

    public VideoPlayAuth getVideoPlayAuth(String userId, String videoId) {
        try {
            Credential credential = credentialService.generateCredential(userId);
            DefaultAcsClient client = aliClientService.initVodClient(credential.getAccessKeyId(),
                    credential.getAssessKeySecret(),
                    credential.getSecrurityToken());

            GetVideoPlayAuthResponse response = getVideoPlayAuth(client, videoId);

            VideoPlayAuth videoPlayAuth = VideoPlayAuth.builder()
                    .metaTitle(response.getVideoMeta().getTitle())
                    .playAuth(response.getPlayAuth())
                    .build();

            return videoPlayAuth;

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return null;
    }
}
