package com.chengxuxiaoba.video.interceptor;

import com.chengxuxiaoba.video.model.CurrentLoginUserModel;
import com.chengxuxiaoba.video.model.JWTToken;
import com.chengxuxiaoba.video.service.imp.AuthenticationService;
import com.chengxuxiaoba.video.util.HttpServletRequestUtil;
import com.chengxuxiaoba.video.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ConfigurationProperties(prefix = "authorization")
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Autowired
    private AuthenticationService authenticationService;

    private List<String> urls;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        authenticationService.setCurrentLoginUserModelInRequest(request);

        return true;
    }
}
