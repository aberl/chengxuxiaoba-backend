package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.constant.ValidationCodeCategory;
import com.chengxuxiaoba.video.model.RegisterRequestVo;
import com.chengxuxiaoba.video.model.Result;
import com.chengxuxiaoba.video.model.ResultCode;
import com.chengxuxiaoba.video.model.ResultMessage;
import com.chengxuxiaoba.video.service.IUserService;
import com.chengxuxiaoba.video.service.IValidationService;
import com.chengxuxiaoba.video.util.StringUtil;
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

    @PostMapping("/account")
    public Result<Boolean> createAccount(@RequestBody RegisterRequestVo registerBody) {
        Boolean isValid = validationService.verifyCode(registerBody.getMobilePhoneNo(), ValidationCodeCategory.register, registerBody.getValidationCode());

        if (!isValid)
            return new Result<Boolean>(ResultCode.Error,false, ResultMessage.ValidationCodeIsIlegal);

        Boolean flag= userService.regier(registerBody.getMobilePhoneNo(),registerBody.getPassword());

        if(!flag)
            return new Result<Boolean>(ResultCode.Error,false, ResultMessage.MobilePhoneNoIsExist);

        return new Result<Boolean>(ResultCode.Success,true, ResultMessage.Success);
    }
}
