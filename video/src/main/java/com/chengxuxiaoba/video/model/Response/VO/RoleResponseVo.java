package com.chengxuxiaoba.video.model.Response.VO;

import com.chengxuxiaoba.video.model.po.Permission;
import com.chengxuxiaoba.video.model.po.Role;

import java.util.List;

public class RoleResponseVo {
    private Integer id;
    private String name;
    private Integer priority;
    private Integer status;

    private List<Permission> permissionList;
    private List<Role> upgradeRoleList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }

    public List<Role> getUpgradeRoleList() {
        return upgradeRoleList;
    }

    public void setUpgradeRoleList(List<Role> upgradeRoleList) {
        this.upgradeRoleList = upgradeRoleList;
    }
}
