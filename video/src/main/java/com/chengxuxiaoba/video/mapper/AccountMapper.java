package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.Account;
import com.chengxuxiaoba.video.model.po.AccountRoleRelationShip;
import com.chengxuxiaoba.video.model.po.AccountVipTimeRange;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface AccountMapper extends  BaseMapper<Account>{
    Integer insert(Account account);

    Integer update(Account account);

    Account getAccount(@Param("userId") Integer userId);

    List<Account> getAccountList(@Param("userIdList") List<Integer> userIdList);

    Account getAccountByMobilePhone(String mobilePhone);

    Integer modifyPasswordByMobilePhone(String mobilePhoneNo, String newPassword);

    Integer buildAccountRoleRelationship(@Param("accountId") Integer accountId,@Param("roleIdList") List<Integer> roleIdList);

    Integer deleteAccountRoleRelationship(@Param("accountId") Integer accountId,@Param("roleId") Integer roleId);

    List<AccountRoleRelationShip> getAccountRoleRelationShip(@Param("accountIdList") List<Integer> accountIdList);

    Integer buildAccountVipTimeRange(@Param("accountId") Integer accountId,@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    Integer updateAccountVipTimeRange(@Param("accountId") Integer accountId,@Param("startDate") Date startDate,@Param("endDate") Date endDate, @Param("status") Integer status);

    AccountVipTimeRange getAccountVipTimeRange(@Param("accountId") Integer accountId);
}

