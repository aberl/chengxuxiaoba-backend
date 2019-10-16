package com.chengxuxiaoba.video.service.imp.ali;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ALiUploadService {

    @Autowired
    private AliClientService aliClientService;

    /**
     * 获取视频上传地址和凭证
     * @param client 发送请求客户端
     * @return CreateUploadVideoResponse 获取视频上传地址和凭证响应数据
     * @throws Exception
     */
    public static CreateUploadVideoResponse createUploadVideo(DefaultAcsClient client) throws Exception {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle("this is a sample");
        request.setFileName("filename.mp4");
        JSONObject userData = new JSONObject();
        JSONObject messageCallback = new JSONObject();
        messageCallback.put("CallbackURL", "http://xxxxx");
        messageCallback.put("CallbackType", "http");
        userData.put("MessageCallback", messageCallback.toString());
        JSONObject extend = new JSONObject();
        extend.put("MyId", "user-defined-id");
        userData.put("Extend", extend.toString());
        request.setUserData(userData.toString());
        return client.getAcsResponse(request);
    }

    // 请求示例
//    public static void main(String[] argv) {
//        DefaultAcsClient client = aliClientService.initVodClient("<Your AccessKeyId>", "<Your AccessKeySecret>");
//        CreateUploadVideoResponse response = new CreateUploadVideoResponse();
//        try {
//            response = createUploadVideo(client);
//            System.out.print("VideoId = " + response.getVideoId() + "\n");
//            System.out.print("UploadAddress = " + response.getUploadAddress() + "\n");
//            System.out.print("UploadAuth = " + response.getUploadAuth() + "\n");
//        } catch (Exception e) {
//            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
//        }
//        System.out.print("RequestId = " + response.getRequestId() + "\n");
//    }
}
