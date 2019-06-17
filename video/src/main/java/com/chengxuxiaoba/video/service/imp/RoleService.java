package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.mapper.RoleMapper;
import com.chengxuxiaoba.video.model.po.Permission;
import com.chengxuxiaoba.video.model.po.Role;
import com.chengxuxiaoba.video.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
       if(role == null || role.getPriority()==0 || role.getPriority() == null)
           return null;

       Integer pirority=role.getPriority();

       return roleMapper.getMorePriorityRoleList(pirority);
    }
}
