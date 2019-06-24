package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.Permission;
import com.chengxuxiaoba.video.model.po.Role;
import com.chengxuxiaoba.video.model.po.RolePayment;

import java.util.List;

public interface RoleMapper {
    Role getRole(Integer id);

    List<Permission> getPermissionList(Integer roleId);

    List<Role> getMorePriorityRoleList(Integer pirority);

    List<RolePayment> getRolePaymentList();
}
