package com.chengxuxiaoba.video.service.imp.ali;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.chengxuxiaoba.video.model.ali.SMSEntity;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class AliSMSService {
    private final static String uniqueSMSRole = "uniqueSMSRoleId";
    @Autowired
    private AliClientService aliClientService;
    public String sendSMSMessage(String[] phoneNumbers, String signName, String templateCode, String templateParam) throws ClientException {
        DefaultAcsClient client = aliClientService.initVodClient(uniqueSMSRole);

        String PhoneNumberStr= StringUtils.join(phoneNumbers);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-shanghai");
        request.putQueryParameter("PhoneNumbers", PhoneNumberStr);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);

        CommonResponse response = client.getCommonResponse(request);

        return response.getData();
    }
}
