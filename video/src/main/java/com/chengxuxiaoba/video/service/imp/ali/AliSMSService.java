package com.chengxuxiaoba.video.service.imp.ali;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.chengxuxiaoba.video.constant.ValidationCodeCategory;
import com.chengxuxiaoba.video.model.ali.SMSEntity;
import com.chengxuxiaoba.video.model.ali.SMSSendResult;
import com.chengxuxiaoba.video.util.JSONUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AliSMSService {
    @Value("${ali.sms.signName}")
    private String signName;

    @Value("${ali.sms.templatecode.register}")
    private String SMS_TemplateCode_register;

    @Value("${ali.sms.templatecode.forgetpwd}")
    private String SMS_TemplateCode_forgetpwd;

    @Value("${ali.accessKey.RAM.accesskeyid}")
    private String STS_AccessKeyId;
    private final static String uniqueSMSRole = "uniqueSMSRoleId";
    @Autowired
    private AliClientService aliClientService;

    public Boolean sendSMSValidationCodeMessage(String[] phoneNumbers, ValidationCodeCategory validationCodeCategory, String validationCode) {
        try {
            String templateParam = String.format("{\"code\":\"%s\"}", validationCode);
            String templateCode = getSMStemplateCode(validationCodeCategory);

            //{"Message":"OK","RequestId":"5C6104D6-B5B0-4158-B295-3ED6E37C2980","BizId":"556120775966407387^0","Code":"OK"}
            String sendResult = sendSMSMessage(phoneNumbers, templateCode, templateParam);

            SMSSendResult smsSendResult = JSONUtil.deserializeJSONStr(sendResult, SMSSendResult.class);

            String messageResult = smsSendResult == null ? null : smsSendResult.getMessage();

            log.info("sendSMSValidationCodeMessage result is {}", messageResult);

            return "ok".equalsIgnoreCase(messageResult);
        } catch (Exception ex) {
            log.error("sendSMSValidationCodeMessage occur exception, phoneNumber:{}, validationCodeCategory:{},validationCode:{},ex:{}",
                    phoneNumbers, validationCodeCategory, validationCode, ex);
            return false;
        }
    }

    public String sendSMSMessage(String[] phoneNumbers, String templateCode, String templateParam) throws ClientException {
        DefaultAcsClient client = aliClientService.initVodClient(uniqueSMSRole);

        String PhoneNumberStr = StringUtils.join(phoneNumbers);

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

    /**
     * get SMS template code by Validation Code Category
     *
     * @param validationCodeCategory
     * @return
     */
    private String getSMStemplateCode(ValidationCodeCategory validationCodeCategory) {
        if (ValidationCodeCategory.register == validationCodeCategory) {
            return SMS_TemplateCode_register;
        }


        if (ValidationCodeCategory.forgetPassword == validationCodeCategory) {
            return SMS_TemplateCode_forgetpwd;
        }

        return null;
    }
}
