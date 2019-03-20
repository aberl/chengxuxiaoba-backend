package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.po.Account;

public interface IUserService {
    Boolean regier(String mobilePhoneNo,String password);

    Boolean isMobilePhoneExist(String mobilePhone);

    Boolean loginByAccount(String mobilePhoneNo,String password);

    Boolean modifyPassword(String mobilePhoneNo, String password);

    Account getUser(String userId);

    Account getUserByMobilePhone(String mobilePhoneNo);
}
