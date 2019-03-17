package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.constant.ValidationCodeCategory;
import com.chengxuxiaoba.video.model.po.ValidationCode;

public interface IValidationService {
    String  generateValidationCode(ValidationCodeCategory category);

    String  generateRegisterValidationCode();

    String  generateLoginValidationCode();

    Boolean sendValidationCode(ValidationCodeCategory category, String mobilePhoneNo);

    ValidationCode getValidationCode(ValidationCodeCategory category, String mobilePhoneNo);
}
