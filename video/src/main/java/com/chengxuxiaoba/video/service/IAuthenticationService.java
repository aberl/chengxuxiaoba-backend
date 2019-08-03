package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.CurrentLoginUserModel;
import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.po.Account;

import javax.servlet.http.HttpServletRequest;

public interface IAuthenticationService {
    String generateCipherPassWord(String mobilePhone, String password);

    KeyValuePair<Boolean, String> validatePassWord(String mobilePhone, String password);

    String generateToken(String mobilePhone);

    CurrentLoginUserModel validateToken(String token);

    KeyValuePair<Boolean, String> validateAuthorization(String token);

    void setCurrentLoginUserModelInRequest(HttpServletRequest request);

    CurrentLoginUserModel getCurrentLoginUserModelFromRequest();
}
