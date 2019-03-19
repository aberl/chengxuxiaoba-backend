package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.Account;

public interface AccountMapper {
    public Integer insert(Account account);

    public Account getAccountByMobilePhone(String mobilePhone);
}
