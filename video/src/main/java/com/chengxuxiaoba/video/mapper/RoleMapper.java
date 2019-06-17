package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.constant.Role;
import com.chengxuxiaoba.video.model.po.Permission;

import java.util.List;

public interface RoleMapper {
    Role getRole(Integer id);

    Permission getPermissionList(Integer roleId);

    List<Role> getMorePriorityRoleList(Integer pirority);
}
