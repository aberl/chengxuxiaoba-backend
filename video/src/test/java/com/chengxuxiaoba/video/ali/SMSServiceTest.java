package com.chengxuxiaoba.video.ali;

import com.aliyuncs.exceptions.ClientException;
import com.chengxuxiaoba.video.service.imp.ali.AliSMSService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SMSServiceTest {

    @Autowired
    private AliSMSService smsService;
    @Test
    public void sendSMSTest()
    {
        String signName="程序小巴";
        String templateCode="SMS_178770038";
        String[] mb={"15001112627"};
        String templateParam ="{\"code\":\"1111\"}";
        try {
            String str = smsService.sendSMSMessage(mb, templateCode,templateParam );
            System.out.println(str);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
