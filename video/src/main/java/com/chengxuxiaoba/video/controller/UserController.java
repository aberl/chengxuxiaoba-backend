package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.constant.ValidationCodeCategory;
import com.chengxuxiaoba.video.model.*;
import com.chengxuxiaoba.video.service.IUserService;
import com.chengxuxiaoba.video.service.IValidationService;
import com.chengxuxiaoba.video.util.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private IValidationService validationService;

    @Autowired
    private IUserService userService;

    @PutMapping("/account")
    public Result<Boolean> createAccount(@RequestBody RegisterRequestVo registerBody) {
        if(!registerBody.getPassword().equals(registerBody.getConfirmPassword()))
            return new Result<Boolean>(ResultCode.Error,false, ResultMessage.PasswordConfirmWrong);

        Boolean isValid = validationService.verifyCode(registerBody.getMobilePhoneNo(), ValidationCodeCategory.register, registerBody.getValidationCode());

        if (!isValid)
            return new Result<Boolean>(ResultCode.Error,false, ResultMessage.ValidationCodeIsIlegal);

        Boolean flag= userService.regier(registerBody.getMobilePhoneNo(),registerBody.getPassword());

        if(!flag)
            return new Result<Boolean>(ResultCode.Error,false, ResultMessage.MobilePhoneNoIsExist);

        return new Result<Boolean>(ResultCode.Success,true, ResultMessage.Success);
    }

    @PostMapping("/account")
    public Result<Boolean> createAccount(@RequestBody LoginRequestVo loginBody) {
        Boolean flag = userService.loginByAccount(loginBody.getMobilePhoneNo(),loginBody.getPassword());

        if(!flag)
            return new Result<Boolean>(ResultCode.Error,false, ResultMessage.UserPWDIsNotMatch);

        return new Result<Boolean>(ResultCode.Success,true, ResultMessage.Success);
    }
}
