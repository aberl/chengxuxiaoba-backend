package com.chengxuxiaoba.video.service.imp.ali;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetImageInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetImageInfoResponse;
import com.chengxuxiaoba.video.model.ali.Credential;
import com.chengxuxiaoba.video.model.ali.ImageInfo;
import com.chengxuxiaoba.video.util.ListUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class AliImageService {
    private final static String uniqueUserRole = "imageuser";

    @Autowired
    private AliCredentialService credentialService;

    @Autowired
    private AliClientService aliClientService;

    private GetImageInfoResponse getImageInfo(DefaultAcsClient client, String imageId) throws Exception {
        GetImageInfoRequest request = new GetImageInfoRequest();
        request.setImageId(imageId);
        return client.getAcsResponse(request);
    }

    public List<ImageInfo> getImageInfoList(List<String> aliImageIdList) {
        if (ListUtil.isNullOrEmpty(aliImageIdList)) {
            return null;
        }
        try {
            Credential credential = credentialService.generateCredential(uniqueUserRole);

            DefaultAcsClient client = aliClientService.initVodClient(credential.getAccessKeyId(),
                    credential.getAssessKeySecret(),
                    credential.getSecrurityToken());

            List<ImageInfo> imageInfoList = new ArrayList<>();

            for (String aliImageId : aliImageIdList) {
                try {
                    GetImageInfoResponse imageInfoResponse = getImageInfo(client, aliImageId);

                    ImageInfo imageInfo = ImageInfo.builder()
                            .imageId(aliImageId)
                            .creationTime(imageInfoResponse.getImageInfo().getCreationTime())
                            .url(imageInfoResponse.getImageInfo().getURL())
                            .fileURL(imageInfoResponse.getImageInfo().getMezzanine().getFileURL())
                            .title(imageInfoResponse.getImageInfo().getTitle())
                            .build();

                    imageInfoList.add(imageInfo);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }

            return imageInfoList;
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return null;
    }

    public ImageInfo getImageInfo(String imageId) {
        if (StringUtil.isNullOrEmpty(imageId)) {
            return null;
        }

        List<String> aliImageIdList = new ArrayList<>(Arrays.asList(imageId));

        List<ImageInfo> imageInfoList = getImageInfoList(aliImageIdList);

        return ListUtil.isNullOrEmpty(imageInfoList) ? null : imageInfoList.get(0);
    }
}
