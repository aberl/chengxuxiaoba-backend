package com.chengxuxiaoba.video.service.imp.ali;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.chengxuxiaoba.video.model.ali.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AliClientService {

    @Autowired
    private AliCredentialService credentialService;
    /**
     * initialize client by accesskey
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     * @throws ClientException
     */
    public DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /**
     * initialize client by STS
     * @param securityToken
     * @return
     * @throws ClientException
     */
    public DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret,String securityToken) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret, securityToken);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /**
     * initialize client by uniqueKey
     * @param uniqueKey
     * @return
     */
    public DefaultAcsClient initVodClient(String uniqueKey) throws ClientException {
        Credential credential = credentialService.generateCredential(uniqueKey);
        DefaultAcsClient client = initVodClient(credential.getAccessKeyId(),
                credential.getAssessKeySecret(),
                credential.getSecrurityToken());

        return client;
    }
}
