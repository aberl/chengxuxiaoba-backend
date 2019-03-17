package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.model.ValidationCodeRequestVo;
import com.chengxuxiaoba.video.service.IValidationService;
import com.chengxuxiaoba.video.util.RegexUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import com.chengxuxiaoba.video.model.Result;
import com.chengxuxiaoba.video.model.ResultCode;
import com.chengxuxiaoba.video.model.ResultMessage;
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

        if (!RegexUtil.isMatchMobilePhoneNo(mobilePhone))
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.MobilePhoneNoIsUnIllegal);

        Boolean flage = validationService.sendValidationCode(validationCode.getCategory(), mobilePhone);

        return new Result<Boolean>(ResultCode.Success, flage, flage ? ResultMessage.Success : ResultMessage.Fail);
    }
}
