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

        if(account != null)
            return  false;

        account=new Account();
        //随机生成6个字母当做用户名
        account.setName(StringUtil.randomGenerateLetterStr(6));
        account.setMobilePhoneNo(mobilePhoneNo);
        account.setPassword(password);

        accountMapper.insert(account);

        return account.getId()>0;
    }

    @Override
    public Boolean loginByAccount(String mobilePhoneNo, String password) {
        Account account = accountMapper.getAccountByMobilePhone(mobilePhoneNo);

        if(account == null)
            return  false;

        if(!account.getPassword().equals(password))
            return  false;

        return  true;
    }
}
