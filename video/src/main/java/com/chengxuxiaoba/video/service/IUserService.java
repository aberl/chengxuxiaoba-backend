package com.chengxuxiaoba.video.service;

public interface IUserService {
    Boolean regier(String mobilePhoneNo,String password);

    Boolean isMobilePhoneExist(String mobilePhone);

    Boolean loginByAccount(String mobilePhoneNo,String password);

    Boolean modifyPassword(String mobilePhoneNo, String password);
}
