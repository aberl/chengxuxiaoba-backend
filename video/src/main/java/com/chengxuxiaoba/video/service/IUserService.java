package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.po.Account;
import com.chengxuxiaoba.video.model.query.UserQuery;

import java.util.List;

public interface IUserService {
    Boolean regier(String mobilePhoneNo,String password);

    Boolean isMobilePhoneExist(String mobilePhone);

    Account loginByAccount(String mobilePhoneNo, String password);

    Boolean modifyPassword(String mobilePhoneNo, String password);

    Account getUser(Integer userId);

    List<Account> getUserList(List<Integer> userIdList);

    List<Account> getAllUserList();

    Account getUserByMobilePhone(String mobilePhoneNo);

    PageResult<Account> getAccountListWithPage(UserQuery userQuery, PageInfo pageInfo);
}
