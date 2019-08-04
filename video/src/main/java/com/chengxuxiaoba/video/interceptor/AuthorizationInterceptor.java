package com.chengxuxiaoba.video.interceptor;

import com.chengxuxiaoba.video.annotation.AuthorizationValidation;
import com.chengxuxiaoba.video.exception.AuthorizationValidationException;
import com.chengxuxiaoba.video.model.CurrentLoginUserModel;
import com.chengxuxiaoba.video.service.imp.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ConfigurationProperties(prefix = "authorization")
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(hasAuthorizationValidation(handler)) {
            validateAuthorization();
        }
        authenticationService.setCurrentLoginUserModelInRequest(request);

        return true;
    }

    private void validateAuthorization() throws Exception {
        CurrentLoginUserModel currentLoginUserModel= authenticationService.getCurrentLoginUserModelFromRequest();

        if(currentLoginUserModel == null)
            throw new AuthorizationValidationException();
    }

    private Boolean hasAuthorizationValidation(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            AuthorizationValidation av = handlerMethod.getMethod().getAnnotation(AuthorizationValidation.class);
            if(av == null)
            {
                av = handlerMethod.getMethod().getDeclaringClass().getAnnotation(AuthorizationValidation.class);
            }

            if(av == null)
                return false;
        }
        return true;
    }
}
