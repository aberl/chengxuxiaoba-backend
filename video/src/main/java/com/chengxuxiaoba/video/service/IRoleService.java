package com.chengxuxiaoba.video.service;

import com.chengxuxiaoba.video.model.po.Permission;
import com.chengxuxiaoba.video.model.po.Role;

import java.util.List;

public interface IRoleService {
    Role getRole(Integer id);

    List<Permission> getPermissionListByRoleId(Integer roleId);

    List<Role> getMorePriorityRoleList(Role role);
}
