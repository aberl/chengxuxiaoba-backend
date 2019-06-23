package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.constant.CommonStatus;
import com.chengxuxiaoba.video.constant.RoleConstant;
import com.chengxuxiaoba.video.constant.ValidationCodeCategory;
import com.chengxuxiaoba.video.model.*;
import com.chengxuxiaoba.video.model.Request.VO.AccountRequestVo;
import com.chengxuxiaoba.video.model.Request.VO.LoginRequestVo;
import com.chengxuxiaoba.video.model.Request.VO.RegisterRequestVo;
import com.chengxuxiaoba.video.model.Response.VO.UserResponseVo;
import com.chengxuxiaoba.video.model.po.Account;
import com.chengxuxiaoba.video.model.po.Role;
import com.chengxuxiaoba.video.model.query.UserQuery;
import com.chengxuxiaoba.video.service.IUserService;
import com.chengxuxiaoba.video.service.IValidationService;
import com.chengxuxiaoba.video.service.IVoService;
import com.chengxuxiaoba.video.util.ListUtil;
import com.chengxuxiaoba.video.util.RegexUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserController extends BaseController {
    @Autowired
    private IValidationService validationService;

    @Autowired
    private IUserService userService;
    @Autowired
    private IVoService voService;

    @PostMapping("/account")
    public Result<Boolean> createAccount(@RequestBody RegisterRequestVo registerBody) {
//        if (!registerBody.getPassword().equals(registerBody.getConfirmPassword()))
//            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.PasswordConfirmWrong);

        Boolean isValid = validationService.verifyCode(registerBody.getMobilePhoneNo(), ValidationCodeCategory.register, registerBody.getValidationCode());

        if (!isValid)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.ValidationCodeIsIlegal);

        RoleConstant[] roles = userService.convertToRoleArray(registerBody.getRoleIdList());
        ;
        Boolean flag = userService.regier(registerBody.getMobilePhoneNo(), registerBody.getPassword(), roles);

        if (!flag)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.MobilePhoneNoIsExist);

        validationService.invalidateCode(registerBody.getMobilePhoneNo(), ValidationCodeCategory.register, registerBody.getValidationCode());

        return new Result<Boolean>(ResultCode.Success, true, ResultMessage.Success);
    }

    @PutMapping("/account")
    public Result<Boolean> updateAccount(@RequestBody AccountRequestVo accountRequestVo) {

        Account account = voService.convertToUser(accountRequestVo);

        Boolean flag = userService.updateAccount(account);

        if (!flag)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.Fail);

        RoleConstant[] roles = userService.convertToRoleArray(accountRequestVo.getRoles());

        if (roles != null && roles.length > 0)
            userService.updateAccountRoleRelationship(account.getId(), roles);

        userService.updateAccountVipTimeRange(accountRequestVo.getId(), accountRequestVo.getVipStartDate(), accountRequestVo.getVipEndDate());

        return new Result<Boolean>(ResultCode.Success, true, ResultMessage.Success);
    }

    @PutMapping("/password")
    public Result<Boolean> rebuildPassword(@RequestBody RegisterRequestVo registerBody) {
        if (!RegexUtil.isMatchMobilePhoneNo(registerBody.getMobilePhoneNo()))
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.MobilePhoneNoIsUnIllegal);

        Boolean isValid = validationService.verifyCode(registerBody.getMobilePhoneNo(), ValidationCodeCategory.forgetPassword, registerBody.getValidationCode());

        if (!isValid)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.ValidationCodeIsIlegal);

        Boolean flag = userService.isMobilePhoneExist(registerBody.getMobilePhoneNo());

        if (!flag)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.MobilePhoneNoIsNotExist);

        Boolean resultFlag = userService.modifyPassword(registerBody.getMobilePhoneNo(), registerBody.getPassword());

        if (!resultFlag)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.Fail);


        validationService.invalidateCode(registerBody.getMobilePhoneNo(), ValidationCodeCategory.forgetPassword, registerBody.getValidationCode());

        return new Result<Boolean>(ResultCode.Success, true, ResultMessage.Success);
    }

    @PostMapping("/token")
    public Result<UserResponseVo> login(@RequestBody LoginRequestVo loginBody) {
        Account account = userService.loginByAccount(loginBody.getMobilePhoneNo(), loginBody.getPassword());

        if (account == null)
            return new Result<UserResponseVo>(ResultCode.Error, null, ResultMessage.UserPWDIsNotMatch);

        UserResponseVo userResponseVo = voService.convertToUserResponseVo(account);

        return new Result<UserResponseVo>(ResultCode.Success, userResponseVo, ResultMessage.Success);
    }

    @GetMapping("")
    public Result<PageResult<UserResponseVo>> getUserInfoList(@RequestParam(value = "query", required = false) String query, @RequestParam("pagenum") Integer pageNum,
                                                              @RequestParam(name = "pagesize", required = false) Integer pageSize,
                                                              @RequestParam(name = "sort", required = false) String sort) {

        PageInfo pageInfo = super.generatePageInfo(pageNum, pageSize, sort);
        UserQuery userQuery = new UserQuery();
        if (CommonStatus.getEnum(query) != null) {
            userQuery.setStatus(CommonStatus.getEnum(query).getValue());
        }
        userQuery.setAccountName(query);
        userQuery.setMobilePhone(query);
        PageResult<Account> accountListWithPage = userService.getAccountListWithPage(userQuery, pageInfo);
        List<Account> accountList = accountListWithPage == null ? null : accountListWithPage.getData();

        List<UserResponseVo> userResponseVoList = voService.convertToUserResponseVo(accountList);

        PageResult<UserResponseVo> result = new PageResult<UserResponseVo>(accountListWithPage.getCurrentNum(), accountListWithPage.getTotalCount(), userResponseVoList);

        return new Result<PageResult<UserResponseVo>>(ResultCode.Success, result, ResultMessage.Success);
    }

    @GetMapping("/{id}")
    public Result<UserResponseVo> getUserInfo(@PathVariable("id") Integer userId) {
        Account account = userService.getUser(userId);

        if (account == null)
            return new Result<UserResponseVo>(ResultCode.Error, null, ResultMessage.UserIsNotExist);

        UserResponseVo userResponseVo = voService.convertToUserResponseVo(account);

        return new Result<UserResponseVo>(ResultCode.Success, userResponseVo, ResultMessage.Success);
    }

    @GetMapping("/mobilephoneno/{mobilephoneno}")
    public Result<UserResponseVo> getUserInfoByMobilePhoneNo(@PathVariable("mobilephoneno") String mobilephoneno) {
        Account account = userService.getUserByMobilePhone(mobilephoneno);

        if (account == null)
            return new Result<UserResponseVo>(ResultCode.Error, null, ResultMessage.UserIsNotExist);

        UserResponseVo userResponseVo = voService.convertToUserResponseVo(account);

        return new Result<UserResponseVo>(ResultCode.Success, userResponseVo, ResultMessage.Success);
    }


    @PutMapping("/updaterole")
    public Result<Boolean> updateAccountRoleRelationShip(@RequestBody AccountRequestVo accountRequestVo) {
        if (accountRequestVo == null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.DataIsNULL);

        if (ListUtil.isNullOrEmpty(accountRequestVo.getRoles()))
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.DataIsNULL);

        Account account = userService.getUser(accountRequestVo.getId());
        if (account == null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.UserIsNotExist);

        RoleConstant[] roleArray = accountRequestVo.getRoles().stream().map(role -> {
            return RoleConstant.getEnum(role);
        }).collect(Collectors.toList()).toArray(new RoleConstant[accountRequestVo.getRoles().size()]);

        Boolean flag = userService.updateAccountRoleRelationship(accountRequestVo.getId(), roleArray);


        return new Result<Boolean>(ResultCode.Success, flag, ResultMessage.Success);

    }

}
