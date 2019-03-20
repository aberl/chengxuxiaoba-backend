package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.Account;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    public Integer insert(Account account);

    public Account getAccount(@Param("userId") String userId);

    public Account getAccountByMobilePhone(String mobilePhone);

    public Integer modifyPasswordByMobilePhone(String mobilePhoneNo,String newPassword);

   // public Integer updateAccount(Account account);
}
