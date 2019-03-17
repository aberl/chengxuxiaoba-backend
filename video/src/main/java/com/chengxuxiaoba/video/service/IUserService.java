package com.chengxuxiaoba.video.service;

public interface IUserService {
    Boolean regier(String mobilePhoneNo,String password);

    Boolean loginByAccount(String mobilePhoneNo,String password);
}
