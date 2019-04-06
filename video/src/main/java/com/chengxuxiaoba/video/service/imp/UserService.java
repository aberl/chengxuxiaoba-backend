package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.mapper.AccountMapper;
import com.chengxuxiaoba.video.model.po.Account;
import com.chengxuxiaoba.video.service.IUserService;
import com.chengxuxiaoba.video.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
    public Account loginByAccount(String mobilePhoneNo, String password) {
        Account account = accountMapper.getAccountByMobilePhone(mobilePhoneNo);

        if (account == null)
            return null;

        if (!account.getPassword().equals(password))
            return null;

        return account;
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
    public List<Account> getUserList(List<Integer> userIdList)
    {
        List<Account> accountList =  accountMapper.getAccountList(userIdList);

        return accountList;
    }

    @Override
    public List<Account> getAllUserList()
    {
        List<Account> accountList =  accountMapper.getAccountList(null);

        return accountList;
    }

    @Override
    public Account getUserByMobilePhone(String mobilePhoneNo)
    {
        Account account =  accountMapper.getAccountByMobilePhone(mobilePhoneNo);

        return account;
    }
}
