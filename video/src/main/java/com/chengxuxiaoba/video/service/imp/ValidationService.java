package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.constant.ValidationCodeCategory;
import com.chengxuxiaoba.video.service.IValidationService;
import com.chengxuxiaoba.video.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ValidationService implements IValidationService {

    @Override
    public String generateValidationCode(ValidationCodeCategory category) {
        if (category == ValidationCodeCategory.register)
            return generateRegisterValidationCode();
        if (category == ValidationCodeCategory.login)
            return generateLoginValidationCode();
        return null;
}

    @Override
    public String generateRegisterValidationCode() {
        String code = StringUtil.randomGenerateNumberStr(4);
        return code;
    }

    @Override
    public String generateLoginValidationCode() {
        String code = StringUtil.randomGenerateNumberStr(4);
        return code;
    }
}
