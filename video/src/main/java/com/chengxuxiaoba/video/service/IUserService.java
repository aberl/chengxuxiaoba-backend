package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.po.Account;

import java.util.List;

public interface IUserService {
    Boolean regier(String mobilePhoneNo,String password);

    Boolean isMobilePhoneExist(String mobilePhone);

    Boolean loginByAccount(String mobilePhoneNo,String password);

    Boolean modifyPassword(String mobilePhoneNo, String password);

    Account getUser(Integer userId);

    List<Account> getUserList(List<Integer> userIdList);

    Account getUserByMobilePhone(String mobilePhoneNo);
}
