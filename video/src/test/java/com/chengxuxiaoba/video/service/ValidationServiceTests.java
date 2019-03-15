package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.constant.ValidationCodeCategory;
import com.chengxuxiaoba.video.service.imp.ValidationService;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidationServiceTests {

    @Autowired
    private ValidationService validationService;

    @Test
    public void generateValidationCode() {
        String registerCode = validationService.generateValidationCode(ValidationCodeCategory.register);
        System.out.println(registerCode);
        assertTrue(registerCode.length() == 4);
        String loginCode = validationService.generateValidationCode(ValidationCodeCategory.login);
        System.out.println(loginCode);
        assertTrue(loginCode.length() == 4);
    }
    @Test
    public void testBaseClass(){
        assertTrue("".equals("com.yubai.Test.App"));
    }
}
