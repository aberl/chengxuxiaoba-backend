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
}
