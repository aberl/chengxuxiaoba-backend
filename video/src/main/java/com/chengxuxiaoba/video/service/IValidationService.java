package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.constant.ValidationCodeCategory;
import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.po.ValidationCode;

public interface IValidationService {
    String generateValidationCode(ValidationCodeCategory category);

    String generateRegisterValidationCode();

    String generateLoginValidationCode();

    String generateForgetPasswordValidationCode();

    KeyValuePair<Boolean, String> isMatchSendCodeCondition(ValidationCodeCategory category, String mobilePhoneNo);

    Boolean sendValidationCode(ValidationCodeCategory category, String mobilePhoneNo);

    ValidationCode getValidationCode(ValidationCodeCategory category, String mobilePhoneNo);

    Boolean verifyCode(String mobilePhoneNo, ValidationCodeCategory category, String code);

    Boolean invalidateCode(String mobilePhoneNo, ValidationCodeCategory category, String code);
}
