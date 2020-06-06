package com.chengxuxiaoba.video.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
public class ApplicationController {
    @GetMapping("/pv")
    public Map<String, List<HttpSession>> hslDescrypt(HttpServletRequest request, HttpServletResponse response) {
        Map<String, List<HttpSession>> userMap=(Map<String, List<HttpSession>>)request.getServletContext().getAttribute("userMap");

       String ua= request.getHeader("User-Agent");

        return userMap;
    }
}
