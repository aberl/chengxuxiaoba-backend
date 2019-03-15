package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.util.RegexUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import com.chengxuxiaoba.video.model.Result;
import com.chengxuxiaoba.video.model.ResultCode;
import com.chengxuxiaoba.video.model.ResultMessage;
import com.chengxuxiaoba.video.model.ValidationCode;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/validation")
public class ValidationController {

    @PutMapping("/code")
    public Result<Boolean> sendValidationCode(@RequestBody ValidationCode validationCode) {
        if (validationCode == null)
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.ParameterError);

        String mobilePhone = validationCode.getMobilePhoneNo();
        String codeCategory = validationCode.getCategory().toString();

        if (StringUtil.isNullOrEmpty(mobilePhone) || StringUtil.isNullOrEmpty(codeCategory))
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.ParameterError);

        if(!RegexUtil.matchMobilePhoneNo(mobilePhone))
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.MobilePhoneNoIsUnIllegal);

        return new Result<Boolean>(ResultCode.Success, true, ResultMessage.Success);
    }
}
