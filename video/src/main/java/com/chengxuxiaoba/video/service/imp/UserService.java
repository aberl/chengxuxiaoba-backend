package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.mapper.AccountMapper;
import com.chengxuxiaoba.video.model.po.Account;
import com.chengxuxiaoba.video.service.IUserService;
import com.chengxuxiaoba.video.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Boolean regier(String mobilePhoneNo, String password) {
        Account account = accountMapper.getAccountByMobilePhone(mobilePhoneNo);

        if (account != null)
            return false;

        account = new Account();
        //随机生成6个字母当做用户名
        account.setName(StringUtil.randomGenerateLetterStr(6));
        account.setMobilePhoneNo(mobilePhoneNo);
        account.setPassword(password);

        accountMapper.insert(account);

        return account.getId() > 0;
    }

    @Override
    public Boolean isMobilePhoneExist(String mobilePhone) {
        return accountMapper.getAccountByMobilePhone(mobilePhone) != null;
    }

    @Override
    public Boolean loginByAccount(String mobilePhoneNo, String password) {
        Account account = accountMapper.getAccountByMobilePhone(mobilePhoneNo);

        if (account == null)
            return false;

        if (!account.getPassword().equals(password))
            return false;

        return true;
    }

    @Override
    public Boolean modifyPassword(String mobilePhoneNo, String password) {
        Account account = accountMapper.getAccountByMobilePhone(mobilePhoneNo);

        if (account == null)
            return false;

        //TODO encrypt password
        String encryptPWD = password;

        Integer primaryKey = accountMapper.modifyPasswordByMobilePhone(mobilePhoneNo,encryptPWD);

        return primaryKey > 0;
    }

    @Override
    public Account getUser(Integer userId)
    {
        Account account =  accountMapper.getAccount(userId);

        return account;
    }

    @Override
    public Account getUserByMobilePhone(String mobilePhoneNo)
    {
        Account account =  accountMapper.getAccountByMobilePhone(mobilePhoneNo);

        return account;
    }
}
