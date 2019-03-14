package com.chengxuxiaoba.video.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    @GetMapping("/test")
    public String test()
    {
        return "hello test";
    }
}
