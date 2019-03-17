package com.chengxuxiaoba.video.controller;

import com.chengxuxiaoba.video.constant.ValidationCodeCategory;
import com.chengxuxiaoba.video.mapper.ValidationCodeMapper;
import com.chengxuxiaoba.video.model.Result;
import com.chengxuxiaoba.video.model.ValidationCodeRequestVo;
import com.chengxuxiaoba.video.model.po.ValidationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @GetMapping("/test")
    public String test() {
        return "hello test";
    }

    @PostMapping("/account")
    public Result<Boolean> sendValidationCode(@RequestBody ValidationCodeRequestVo validationCode) {

        return null;
    }
}
