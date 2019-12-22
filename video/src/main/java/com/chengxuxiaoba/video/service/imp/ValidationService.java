package com.chengxuxiaoba.video.service.imp;

import com.chengxuxiaoba.video.constant.ValidationCodeCategory;
import com.chengxuxiaoba.video.mapper.ValidationCodeMapper;
import com.chengxuxiaoba.video.model.KeyValuePair;
import com.chengxuxiaoba.video.model.ResultMessage;
import com.chengxuxiaoba.video.model.po.ValidationCode;
import com.chengxuxiaoba.video.service.IUserService;
import com.chengxuxiaoba.video.service.IValidationService;
import com.chengxuxiaoba.video.service.imp.ali.AliSMSService;
import com.chengxuxiaoba.video.util.DateUtil;
import com.chengxuxiaoba.video.util.RegexUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class ValidationService implements IValidationService {
    @Autowired
    private IUserService userService;

    @Value("${validationcode.expiretime}")
    private Integer expireTime;

    @Value("${validationcode.sms.limitCountPerday}")
    private Integer limitCountPerday;

    @Autowired
    ValidationCodeMapper validationCodeMapper;

    @Autowired
    AliSMSService aliSMSService;

    @Override
    public String generateValidationCode(ValidationCodeCategory category) {
        if (category == ValidationCodeCategory.register)
            return generateRegisterValidationCode();
        if (category == ValidationCodeCategory.login)
            return generateLoginValidationCode();
        if (category == ValidationCodeCategory.forgetPassword)
            return generateForgetPasswordValidationCode();
        return null;
    }

    @Override
    public String generateRegisterValidationCode() {
        String code = StringUtil.randomGenerateNumberStr(4);
        return code;
    }

    @Override
    public String generateLoginValidationCode() {
        String code = StringUtil.randomGenerateNumberStr(4);
        return code;
    }

    @Override
    public String generateForgetPasswordValidationCode() {
        String code = StringUtil.randomGenerateNumberStr(4);
        return code;
    }

    @Override
    public KeyValuePair<Boolean, String> isMatchSendCodeCondition(ValidationCodeCategory category, String mobilePhoneNo) {
        if (!RegexUtil.isMatchMobilePhoneNo(mobilePhoneNo))
            return new KeyValuePair<Boolean, String>(false, ResultMessage.MobilePhoneNoIsUnIllegal);

        Integer sendCountToday=validationCodeMapper.getSendMsgCountToday(mobilePhoneNo);
        if(sendCountToday>limitCountPerday)
        {
            return new KeyValuePair<Boolean, String>(false, ResultMessage.CURRENTMOBILESURPASSSENDLIMITCOUNT);
        }

        if (category == ValidationCodeCategory.register) {
            if (userService.isMobilePhoneExist(mobilePhoneNo))
                return new KeyValuePair<Boolean, String>(false, ResultMessage.MobilePhoneNoIsExist);
        }

        if (category == ValidationCodeCategory.forgetPassword) {
            if (!userService.isMobilePhoneExist(mobilePhoneNo))
                return new KeyValuePair<Boolean, String>(false, ResultMessage.MobilePhoneNoIsNotExist);
        }

        return new KeyValuePair<Boolean, String>(true, ResultMessage.Success);
    }

    @Override
    public Boolean sendValidationCode(ValidationCodeCategory category, String mobilePhoneNo) {
        ValidationCode codeModel = validationCodeMapper.getEffectiveCode(mobilePhoneNo, category.toString());

        if (codeModel == null) {
            String code = generateValidationCode(category);
            codeModel = new ValidationCode();
            codeModel.setCategory(category.toString());
            codeModel.setActive(Boolean.TRUE);
            codeModel.setCreateTime(new Date());
            codeModel.setExpireTime(DateUtil.addSECOND(codeModel.getCreateTime(), expireTime));
            codeModel.setMobilePhoneNo(mobilePhoneNo);
            codeModel.setValidationCode(code);
            validationCodeMapper.insert(codeModel);
        }

        String[] phoneNumbers = {mobilePhoneNo};
        Boolean sendFlag= aliSMSService.sendSMSValidationCodeMessage(phoneNumbers, category, codeModel.getValidationCode());

        return sendFlag;
    }

    @Override
    public ValidationCode getValidationCode(ValidationCodeCategory category, String mobilePhoneNo) {
        if (category == null)
            return null;

        if (StringUtil.isNullOrEmpty(mobilePhoneNo))
            return null;

        ValidationCode validationCode = validationCodeMapper.getEffectiveCode(mobilePhoneNo, category.toString());

        return validationCode;
    }

    @Override
    public Boolean verifyCode(String mobilePhoneNo, ValidationCodeCategory category, String code) {
        if (StringUtil.isNullOrEmpty(code))
            return false;

        ValidationCode validationCode = validationCodeMapper.getEffectiveCode(mobilePhoneNo, category.toString());

        if (validationCode == null)
            return false;

        return validationCode.getValidationCode().toLowerCase().equals(code.toLowerCase());
    }

    public Boolean invalidateCode(String mobilePhoneNo, ValidationCodeCategory category, String code) {
        return validationCodeMapper.invalidateCode(mobilePhoneNo, category.toString(), code) > 0;
    }
}
