package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.model.*;
import com.chengxuxiaoba.video.service.IUserService;
import com.chengxuxiaoba.video.service.IValidationService;
import com.chengxuxiaoba.video.util.RegexUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/validation")
public class ValidationController {

    @Autowired
    private IValidationService validationService;

    @PostMapping("/code")
    public Result<Boolean> sendValidationCode(@RequestBody ValidationCodeRequestVo validationCode) {
        if (validationCode == null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.ParameterError);

        String mobilePhone = validationCode.getMobilePhoneNo();
        String codeCategory = validationCode.getCategory().toString();

        if (StringUtil.isNullOrEmpty(mobilePhone) || StringUtil.isNullOrEmpty(codeCategory))
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.ParameterError);

        KeyValuePair<Boolean, String> sendCondition = validationService.isMatchSendCodeCondition(validationCode.getCategory(), validationCode.getMobilePhoneNo());

        if (!sendCondition.getKey())
            return new Result<Boolean>(ResultCode.Error, false, sendCondition.getValue());

        Boolean flag = validationService.sendValidationCode(validationCode.getCategory(), mobilePhone);

        return new Result<Boolean>(ResultCode.Success, flag, flag ? ResultMessage.Success : ResultMessage.Fail);
    }
}
