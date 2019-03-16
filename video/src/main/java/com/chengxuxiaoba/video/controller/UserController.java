package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.constant.ValidationCodeCategory;
import com.chengxuxiaoba.video.mapper.ValidationCodeMapper;
import com.chengxuxiaoba.video.model.po.ValidationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private ValidationCodeMapper validationCodeMapper;

    @GetMapping("/test")
    public String test() {
        ValidationCode validationCode=new ValidationCode();
        validationCode.setCategory(ValidationCodeCategory.register.toString());
        validationCode.setActive(Boolean.TRUE);
        validationCode.setCreateTime(new Date());
        validationCode.setExpireTime(new Date());
        validationCode.setMobilePhoneNo("15001112820");
        validationCode.setValidationCode("12e444");

        Integer key = validationCodeMapper.insert(validationCode);

        Integer id=validationCode.getId();

        String  vc=validationCodeMapper.getValidationCode();
        return
                "hello test";
    }

}
