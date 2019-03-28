package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.Account;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountMapper {
    Integer insert(Account account);

    Account getAccount(@Param("userId") Integer userId);

    List<Account> getAccountList(@Param("userIdList") List<Integer> userIdList);

    Account getAccountByMobilePhone(String mobilePhone);

    Integer modifyPasswordByMobilePhone(String mobilePhoneNo, String newPassword);

    //  Integer updateAccount(Account account);
}
