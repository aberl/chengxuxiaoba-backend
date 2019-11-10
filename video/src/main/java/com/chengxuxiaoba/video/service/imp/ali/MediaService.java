package com.chengxuxiaoba.video.service.imp.ali;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.kms.model.v20160120.DecryptRequest;
import com.aliyuncs.kms.model.v20160120.DecryptResponse;
import com.aliyuncs.kms.model.v20160120.GenerateDataKeyRequest;
import com.aliyuncs.kms.model.v20160120.GenerateDataKeyResponse;
import com.aliyuncs.vod.model.v20170321.SubmitTranscodeJobsRequest;
import com.aliyuncs.vod.model.v20170321.SubmitTranscodeJobsResponse;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

@Service
public class MediaService {
    @Autowired
    private AliClientService aliClientService;

    private final static String uniqueMediaRole = "uniqueMediaRoleId";

    @Value("${ali.video.encryptionTemplateId}")
    private String encryptionTemplateId;

    @Value("${ali.video.nontranscodeTemplateId}")
    private String nontranscodeTemplateId;

    //点播提供生成秘钥的service key，在用户的秘钥管理服务中可看到描述为vod的加密key
    @Value("${ali.kms.serviceKey}")
    private String serviceKey;

    @Value("${ali.hls.decryptUrl}")
    private String hlsDecryptUrl;

    public SubmitTranscodeJobsResponse submitTranscodeJobs(String videoId, String cipherText) throws Exception {
        DefaultAcsClient client = aliClientService.initVodClient(uniqueMediaRole);
        SubmitTranscodeJobsRequest request = new SubmitTranscodeJobsRequest();
        //需要转码的视频ID
        request.setVideoId(videoId);
        //转码模板ID
        request.setTemplateGroupId(encryptionTemplateId);
        //构建需要替换的水印参数(只有需要替换水印相关信息才需要构建)
        //JSONObject overrideParams = buildOverrideParams();
        //覆盖参数，暂只支持水印部分参数替换(只有需要替换水印相关信息才需要传递)
        //request.setOverrideParams(overrideParams.toJSONString());
        //构建标准加密配置参数(只有标准加密才需要构建)
        JSONObject encryptConfig = buildEncryptConfig(client, cipherText);
        //HLS标准加密配置(只有标准加密才需要传递)
        request.setEncryptConfig(encryptConfig.toJSONString());
        return client.getAcsResponse(request);
    }

    /**
     * 1、构建覆盖参数，目前只支持图片水印文件地址、文字水印的内容覆盖；
     * 2、需要替换的水印信息对应水印ID必须是关联在指定的模板ID(即TranscodeTemplateId)中；
     * 3、不支持通过媒体处理接口去增加一个没有关联上的水印
     * 注意：图片水印的文件存储源站需要和发起转码的视频存储源站一致
     *
     * @return
     */
    public JSONObject buildOverrideParams() {
        JSONObject overrideParams = new JSONObject();
        JSONArray watermarks = new JSONArray();
        //图片水印文件地址替换
        JSONObject watermark1 = new JSONObject();
        //模板上面关联需要替换的水印文件图片水印ID
        watermark1.put("WatermarkId", "2ea587477c5a1bc8b5742d7");
        //需要替换成对应图片水印文件的OSS地址，水印文件存储源站需要和视频存储源站一致
        watermark1.put("FileUrl", "https://outin-40564284ef05113e1403e7.oss-cn-shanghai.aliyuncs.com/watermarks/02A1B22DF25D46C3C725A4-6-2.png");
        watermarks.add(watermark1);
        //文字水印内容替换
        JSONObject watermark2 = new JSONObject();
        //模板上面关联需要替换内容的文字水印ID
        watermark2.put("WatermarkId", "d297ba31ac5242d2071bf7");
        //需要替换成对应的内容
        watermark2.put("Content", "用户ID：66666");
        watermarks.add(watermark2);
        overrideParams.put("Watermarks", watermarks);
        return overrideParams;
    }

    /**
     * 构建HLS标准加密的配置信息
     *
     * @return
     * @throws ClientException
     */
    public JSONObject buildEncryptConfig(DefaultAcsClient client, String cipherText) throws ClientException {
        //点播给用户在KMS(秘钥管理服务)中的Service Key，可在用户秘钥管理服务对应的区域看到描述为vod的service key
        //随机生成一个加密的秘钥，返回的response包含明文秘钥以及密文秘钥，
        //视频标准加密只需要传递密文秘钥即可
        //GenerateDataKeyResponse response = generateDataKey(client);
        JSONObject encryptConfig = new JSONObject();
        //解密接口地址，该参数需要将每次生成的密文秘钥与接口URL拼接生成，表示每个视频的解密的密文秘钥都不一样
        //至于Ciphertext这个解密接口参数的名称，用户可自行制定，这里只作为参考参数名称
        encryptConfig.put("DecryptKeyUri", String.format("%s?Ciphertext=%s", hlsDecryptUrl, cipherText));

        //秘钥服务的类型，目前只支持KMS
        encryptConfig.put("KeyServiceType", "KMS");
        //密文秘钥
        encryptConfig.put("CipherText", cipherText);
        return encryptConfig;
    }

    /**
     * 生成加密需要的秘钥，response中包含密文秘钥和明文秘钥，用户只需要将密文秘钥传递给点播即可
     * 注意：KeySpec 必须传递AES_128，且不能设置NumberOfBytes
     *
     * @param client KMS-SDK客户端
     * @return
     * @throws ClientException
     */
    public GenerateDataKeyResponse generateDataKey(DefaultAcsClient client) throws ClientException {
        client = client == null ? aliClientService.initVodClient(uniqueMediaRole) : client;
        GenerateDataKeyRequest request = new GenerateDataKeyRequest();
        request.setKeyId(serviceKey);
        request.setKeySpec("AES_128");
        return client.getAcsResponse(request);
    }

    /**
     * 调用KMS decrypt接口解密，并将明文base64decode
     * @param ciphertext
     * @return
     */
    public byte[] decrypt(String ciphertext) {
        DecryptRequest request = new DecryptRequest();
        request.setCiphertextBlob(ciphertext);
        request.setProtocol(ProtocolType.HTTPS);
        try {
            DefaultAcsClient client = aliClientService.initVodClient(uniqueMediaRole);
            DecryptResponse response = client.getAcsResponse(request);
            String plaintext = response.getPlaintext();
            //注意：需要base64 decode
            return Base64.decodeBase64(plaintext);
        } catch (ClientException e) {
            e.printStackTrace();
            return null;
        }
    }
}
