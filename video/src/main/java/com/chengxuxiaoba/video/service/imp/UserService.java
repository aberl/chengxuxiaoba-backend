package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.constant.CommonStatus;
import com.chengxuxiaoba.video.constant.Role;
import com.chengxuxiaoba.video.mapper.AccountMapper;
import com.chengxuxiaoba.video.model.PageInfo;
import com.chengxuxiaoba.video.model.PageResult;
import com.chengxuxiaoba.video.model.po.Account;
import com.chengxuxiaoba.video.model.po.AccountRoleRelationShip;
import com.chengxuxiaoba.video.model.po.AccountVipTimeRange;
import com.chengxuxiaoba.video.model.query.UserQuery;
import com.chengxuxiaoba.video.service.IBaseService;
import com.chengxuxiaoba.video.service.IUserService;
import com.chengxuxiaoba.video.util.ListUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService extends IBaseService<Account> implements IUserService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    @Transactional
    public Boolean regier(String mobilePhoneNo, String password, Role... roles) {
        Account account = accountMapper.getAccountByMobilePhone(mobilePhoneNo);

        if (account != null)
            return false;

        account = new Account();
        //随机生成6个字母当做用户名
        account.setName(StringUtil.randomGenerateLetterStr(6));
        account.setMobilePhoneNo(mobilePhoneNo);
        account.setPassword(password);

        accountMapper.insert(account);

        Integer accountId = account.getId();

        if (roles != null && roles.length > 0) {
            List<Integer> roleList = new ArrayList<>();
            for (Role role : roles) {
                if (role != null && !roleList.contains(role))
                    roleList.add(role.getValue());
            }
            accountMapper.buildAccountRoleRelationship(accountId, roleList);
        }
        return accountId > 0;
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

        Integer primaryKey = accountMapper.modifyPasswordByMobilePhone(mobilePhoneNo, encryptPWD);

        return primaryKey > 0;
    }

    @Override
    public Account getUser(Integer userId) {
        Account account = accountMapper.getAccount(userId);

        return account;
    }

    @Override
    public List<Account> getUserList(List<Integer> userIdList) {
        List<Account> accountList = accountMapper.getAccountList(userIdList);

        return accountList;
    }

    @Override
    public List<Account> getAllUserList() {
        List<Account> accountList = accountMapper.getAccountList(null);

        return accountList;
    }

    @Override
    public Account getUserByMobilePhone(String mobilePhoneNo) {
        Account account = accountMapper.getAccountByMobilePhone(mobilePhoneNo);

        return account;
    }

    @Override
    public PageResult<Account> getAccountListWithPage(UserQuery userQuery, PageInfo pageInfo) {
        if (pageInfo == null)
            return null;

        userQuery.setPageInfo(pageInfo);

        PageResult<Account> pageResult = super.getListByQuery(accountMapper, userQuery);

        return pageResult;
    }

    @Override
    public Boolean updateAccount(Account account) {
        Integer primaryKey = accountMapper.update(account);

        return primaryKey > 0;
    }

    @Override
    public List<AccountRoleRelationShip> getAccountRoleRelationShipList(List<Integer> accountIdList) {
        if (ListUtil.isNullOrEmpty(accountIdList))
            return null;

        List<AccountRoleRelationShip> _list = accountMapper.getAccountRoleRelationShip(accountIdList);

        return _list;
    }

    @Override
    public Boolean updateAccountRoleRelationship(Integer accountId, Role... roleArray) {
        Integer primaryKey = 0;

        if (roleArray == null || roleArray.length == 0)
            return false;

        if (accountId == null || accountId <= 0)
            return false;

        //delete all relationship data
        accountMapper.deleteAccountRoleRelationship(accountId, null);

        if (roleArray != null && roleArray.length > 0) {
            List<Integer> roleList = new ArrayList<>();
            for (Role role : roleArray) {
                if (role != null && !roleList.contains(role))
                    roleList.add(role.getValue());
            }
            accountMapper.buildAccountRoleRelationship(accountId, roleList);
        }

        return primaryKey > 0;
    }

    @Override
    public Boolean updateAccountVipTimeRange(Integer accountId, Date startDateTime, Date endDateTime)
    {
        if(startDateTime == null || endDateTime == null)
            return false;

        if(startDateTime.compareTo(endDateTime) >0)
            return false;

        if(accountId == null || accountId==0)
            return false;

        AccountVipTimeRange vipTimeRange = getVipTimeRange(accountId);
        if(vipTimeRange == null)
            accountMapper.buildAccountVipTimeRange(accountId, startDateTime,endDateTime);
        else
            accountMapper.updateAccountVipTimeRange(accountId, startDateTime,endDateTime,1);

        return true;
    }

    @Override
    public AccountVipTimeRange getVipTimeRange(Integer accountId)
    {
        return accountMapper.getAccountVipTimeRange(accountId);
    }

    @Override
    public Role[] convertToRoleArray(List<Integer> roleList) {
        if (ListUtil.isNullOrEmpty(roleList))
            return null;

        Role[] roles = new Role[roleList.size()];

        for (Integer index = 0; index < roleList.size(); index++) {
            roles[index] = Role.getEnum(roleList.get(index));
        }

        return roles;
    }
}
