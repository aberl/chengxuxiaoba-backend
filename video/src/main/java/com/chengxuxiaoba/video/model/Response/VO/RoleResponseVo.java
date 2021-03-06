package com.chengxuxiaoba.video.model.Response.VO;

import com.chengxuxiaoba.video.model.po.Permission;
import com.chengxuxiaoba.video.model.po.Role;

import java.util.List;

public class RoleResponseVo {
    private Integer id;
    private String name;
    private String description;
    private Integer priority;
    private Integer status;

    private List<Permission> permissionList;
    private List<RoleResponseVo> upgradeRoleList;

    private Boolean isNeedUpgrade;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<RoleResponseVo> getUpgradeRoleList() {
        return upgradeRoleList;
    }

    public void setUpgradeRoleList(List<RoleResponseVo> upgradeRoleList) {
        this.upgradeRoleList = upgradeRoleList;
    }

    public Boolean getNeedUpgrade() {
        return isNeedUpgrade;
    }

    public void setNeedUpgrade(Boolean needUpgrade) {
        isNeedUpgrade = needUpgrade;
    }
}
