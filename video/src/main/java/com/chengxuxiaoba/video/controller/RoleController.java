package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.constant.RoleConstant;
import com.chengxuxiaoba.video.model.Response.VO.RolePaymentResponseVo;
import com.chengxuxiaoba.video.model.Response.VO.RoleResponseVo;
import com.chengxuxiaoba.video.model.Result;
import com.chengxuxiaoba.video.model.ResultCode;
import com.chengxuxiaoba.video.model.ResultMessage;
import com.chengxuxiaoba.video.model.po.Role;
import com.chengxuxiaoba.video.model.po.RolePayment;
import com.chengxuxiaoba.video.service.IRoleService;
import com.chengxuxiaoba.video.service.IVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping(value = "/role")
public class RoleController {
    @Autowired
    private IVoService voService;
    @Autowired
    private IRoleService roleService;

    @GetMapping("/roles")
    public Result<Map<Integer, String>> getRoleList() {
        Map<Integer, String> ret = RoleConstant.getAllMap();
        return new Result<Map<Integer, String>>(ResultCode.Success, ret, ResultMessage.Success);
    }

    @GetMapping("/{id}")
    public Result<RoleResponseVo> getRole(@PathVariable("id") Integer roleId) {
        Role role = roleService.getRole(roleId);

        RoleResponseVo roleResponseVo = voService.convertToRoleResponseVo(role);

        return new Result<RoleResponseVo>(ResultCode.Success, roleResponseVo, ResultMessage.Success);
    }

    @GetMapping("/{id}/rolepaymentlist")
    public Result<List<RolePaymentResponseVo>> getRolePaymentList(@PathVariable("id") Integer roleId) {
        List<RolePayment> _rolePaymentList = roleService.getRolePaymentList(roleId);

        List<RolePaymentResponseVo> responseVoList = voService.convertToRolePaymentResponseVo(_rolePaymentList);

        return new Result<List<RolePaymentResponseVo>>(ResultCode.Success, responseVoList, ResultMessage.Success);
    }

}
