package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.model.*;
import com.chengxuxiaoba.video.model.Request.VO.ValidationCodeRequestVo;
import com.chengxuxiaoba.video.model.lsm.VerifyResult;
import com.chengxuxiaoba.video.service.IValidationService;
import com.chengxuxiaoba.video.service.imp.lsm.LuoSiMaoService;
import com.chengxuxiaoba.video.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/validation")
public class ValidationController {

    @Autowired
    private IValidationService validationService;

    @Autowired
    private LuoSiMaoService luoSiMaoService;

    @PostMapping("/code")
    public Result<Boolean> sendValidationCode(@RequestBody ValidationCodeRequestVo validationCode) {
        if (validationCode == null) {
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.ParameterError);
        }

        String captchaStr = validationCode.getCaptcha();

        VerifyResult verifyResult = luoSiMaoService.verifyCaptcha(captchaStr);
        if (!verifyResult.isSuccess()) {
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.CAPTCHAERROR);
        }

        String mobilePhone = validationCode.getMobilePhoneNo();
        String codeCategory = validationCode.getCategory().toString();

        if (StringUtil.isNullOrEmpty(mobilePhone) || StringUtil.isNullOrEmpty(codeCategory)) {
            return new Result<Boolean>(ResultCode.Error, false, ResultMessage.ParameterError);
        }

        KeyValuePair<Boolean, String> sendCondition = validationService.isMatchSendCodeCondition(validationCode.getCategory(), validationCode.getMobilePhoneNo());

        if (!sendCondition.getKey()) {
            return new Result<Boolean>(ResultCode.Error, false, sendCondition.getValue());
        }

        Boolean flag = validationService.sendValidationCode(validationCode.getCategory(), mobilePhone);

        if (!flag) {
            return new Result<Boolean>(ResultCode.Error, flag, ResultMessage.SMSSENDFAIL);
        }

        return new Result<Boolean>(ResultCode.Success, flag, ResultMessage.Success);
    }
}
