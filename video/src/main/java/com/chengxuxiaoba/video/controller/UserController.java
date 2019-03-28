package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.constant.ValidationCodeCategory;
import com.chengxuxiaoba.video.model.*;
import com.chengxuxiaoba.video.model.Request.VO.LoginRequestVo;
import com.chengxuxiaoba.video.model.Request.VO.RegisterRequestVo;
import com.chengxuxiaoba.video.model.Response.VO.UserResponseVo;
import com.chengxuxiaoba.video.model.po.Account;
import com.chengxuxiaoba.video.service.IUserService;
import com.chengxuxiaoba.video.service.IValidationService;
import com.chengxuxiaoba.video.service.IVoService;
import com.chengxuxiaoba.video.util.RegexUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private IValidationService validationService;

    @Autowired
    private IUserService userService;
    @Autowired
    private IVoService voService;

    @PostMapping("/account")
    public Result<Boolean> createAccount(@RequestBody RegisterRequestVo registerBody) {
        if (!registerBody.getPassword().equals(registerBody.getConfirmPassword()))
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.PasswordConfirmWrong);

        Boolean isValid = validationService.verifyCode(registerBody.getMobilePhoneNo(), ValidationCodeCategory.register, registerBody.getValidationCode());

        if (!isValid)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.ValidationCodeIsIlegal);

        Boolean flag = userService.regier(registerBody.getMobilePhoneNo(), registerBody.getPassword());

        if (!flag)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.MobilePhoneNoIsExist);

        validationService.invalidateCode(registerBody.getMobilePhoneNo(), ValidationCodeCategory.register, registerBody.getValidationCode());

        return new Result<Boolean>(ResultCode.Success, true, ResultMessage.Success);
    }

    @PutMapping("/password")
    public Result<Boolean> rebuildPassword(@RequestBody RegisterRequestVo registerBody) {
        if (!RegexUtil.isMatchMobilePhoneNo(registerBody.getMobilePhoneNo()))
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.MobilePhoneNoIsUnIllegal);

        if (!registerBody.getPassword().equals(registerBody.getConfirmPassword()))
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.PasswordConfirmWrong);

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

        return new Result<Boolean>(ResultCode.Error, true, ResultMessage.Success);
    }

    @PostMapping("/token")
    public Result<Boolean> login(@RequestBody LoginRequestVo loginBody) {
        Boolean flag = userService.loginByAccount(loginBody.getMobilePhoneNo(), loginBody.getPassword());

        if (!flag)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.UserPWDIsNotMatch);

        return new Result<Boolean>(ResultCode.Success, true, ResultMessage.Success);
    }

    @GetMapping("/{id}")
    public Result<UserResponseVo> getUserInfo(@PathVariable("id") Integer userId)
    {
      Account account = userService.getUser(userId);

      if(account == null)
          return new Result<UserResponseVo>(ResultCode.Error, null, ResultMessage.UserIsNotExist);

        UserResponseVo userResponseVo=voService.convertToUserResponseVo(account);

        return new Result<UserResponseVo>(ResultCode.Success, userResponseVo, ResultMessage.Success);
    }
}
