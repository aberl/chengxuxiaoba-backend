package com.chengxuxiaoba.video.service.imp.ali;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.ProtocolType;
import com.chengxuxiaoba.video.model.ali.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AliCredentialService {

    @Autowired
    private AliRoleService roleService;

    @Value("${ali.accessKey.RAM.accesskeyid}")
    private String STS_AccessKeyId;
    @Value("${ali.accessKey.RAM.AccessKeySecret}")
    private String STS_AccessKeySecret;

    @Value("${ali.accessKey.RAM.normalRoleArn}")
    private String normalRoleArn;

    private static String policy = "{\n" +
            "  \"Version\": \"1\",\n" +
            "  \"Statement\": [\n" +
            "    {\n" +
            "      \"Action\": \"vod:*\",\n" +
            "      \"Resource\": \"*\",\n" +
            "      \"Effect\": \"Allow\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public Credential generateCredential(String uniqueUserRole) {
        try {
            AssumeRoleResponse response = roleService.assumeRole(STS_AccessKeyId, STS_AccessKeySecret,
                    normalRoleArn, uniqueUserRole, policy, ProtocolType.HTTPS);

            Credential credential=Credential.builder()
                    .accessKeyId(response.getCredentials().getAccessKeyId())
                    .assessKeySecret(response.getCredentials().getAccessKeySecret())
                    .secrurityToken(response.getCredentials().getSecurityToken())
                    .expiration(response.getCredentials().getExpiration())
                    .build();

            return credential;
        } catch (ClientException e) {
            System.out.println("Failed to get a token.");
            System.out.println("Error code: " + e.getErrCode());
            System.out.println("Error message: " + e.getErrMsg());
        }

        return null;
    }
}
