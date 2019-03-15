package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.constant.ValidationCodeCategory;

public interface IValidationService {
    String  generateValidationCode(ValidationCodeCategory category);

    String  generateRegisterValidationCode();

    String  generateLoginValidationCode();
}
