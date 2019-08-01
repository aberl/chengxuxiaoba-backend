package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.po.Account;

public interface IAuthenticationService {
    String generateCipherPassWord(String mobilePhone, String password);

    KeyValuePair<Boolean, String> validatePassWord(String mobilePhone, String password);

    String generateToken(String mobilePhone);
    
    KeyValuePair<Boolean, String> validateAuthorization(String token);
}
