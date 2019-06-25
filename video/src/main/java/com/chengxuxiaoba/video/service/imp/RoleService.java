package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.constant.RolePaymentTypeEnum;
import com.chengxuxiaoba.video.mapper.RoleMapper;
import com.chengxuxiaoba.video.model.po.Permission;
import com.chengxuxiaoba.video.model.po.Role;
import com.chengxuxiaoba.video.model.po.RolePayment;
import com.chengxuxiaoba.video.service.IRoleService;
import com.chengxuxiaoba.video.util.DateUtil;
import com.chengxuxiaoba.video.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role getRole(Integer id) {
        return roleMapper.getRole(id);
    }

    @Override
    public List<Permission> getPermissionListByRoleId(Integer roleId) {
        return roleMapper.getPermissionList(roleId);
    }

    @Override
    public List<Role> getMorePriorityRoleList(Role role) {
        if (role == null || role.getPriority() == null)
            return null;

        Integer pirority = role.getPriority();

        return roleMapper.getMorePriorityRoleList(pirority);
    }

    @Override
    public List<RolePayment> getRolePaymentList(Integer... roleIdArray) {
        List<RolePayment> rolePaymentList = roleMapper.getRolePaymentList();

        if (roleIdArray == null || roleIdArray.length == 0)
            return rolePaymentList;

        if (ListUtil.isNullOrEmpty(rolePaymentList))
            return rolePaymentList;

        List<RolePayment> _rolePaymentList = new ArrayList<>();

        List<Integer> _roleIdList = Arrays.asList(roleIdArray);

        rolePaymentList.forEach(rolePayment -> {
            if (_roleIdList.contains(rolePayment.getRoleId())) {
                _rolePaymentList.add(rolePayment);
            }
        });

        return _rolePaymentList;
    }

    @Override
    public Date generateEndDateForRolePayment(RolePaymentTypeEnum rolePaymentTypeEnum) {
        if (rolePaymentTypeEnum == null)
            return null;

        Date restult=null;

        if(rolePaymentTypeEnum == RolePaymentTypeEnum.MONTH)
        {
            restult = DateUtil.addMonth(new Date(),1);
        }

        if(rolePaymentTypeEnum == RolePaymentTypeEnum.SEASON)
        {
            Integer targetAddMonthCount=3+1;
            restult = DateUtil.addMonth(new Date(),targetAddMonthCount);
        }

        if(rolePaymentTypeEnum == RolePaymentTypeEnum.YEAR)
        {
            Integer targetAddMonthCount=12+12;
            restult = DateUtil.addMonth(new Date(),targetAddMonthCount);
        }

        return restult;
    }
}
