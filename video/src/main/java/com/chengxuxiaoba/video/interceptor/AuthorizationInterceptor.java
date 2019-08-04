package com.chengxuxiaoba.video.interceptor;

import com.chengxuxiaoba.video.annotation.AuthorizationValidation;
import com.chengxuxiaoba.video.constant.RoleConstant;
import com.chengxuxiaoba.video.exception.AuthorizationValidationException;
import com.chengxuxiaoba.video.model.CurrentLoginUserModel;
import com.chengxuxiaoba.video.model.po.Role;
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

        AuthorizationValidation authorizationValidation = hasAuthorizationValidation(handler);
        if (authorizationValidation != null) {
            RoleConstant role = authorizationValidation.role();

            authenticationService.setCurrentLoginUserModelInRequest(request);

            validateAuthorization(role);
        }

        return true;
    }

    private void validateAuthorization(RoleConstant role) throws Exception {
        CurrentLoginUserModel currentLoginUserModel = authenticationService.getCurrentLoginUserModelFromRequest();

        if (currentLoginUserModel == null)
            throw new AuthorizationValidationException();

        if (role != null) {
            Integer _role = currentLoginUserModel.getRole();

            if (role == RoleConstant.ADMIN) {
                if (_role != role.getValue())
                    throw new AuthorizationValidationException();

            }

        }
    }

    private RoleConstant getRole(AuthorizationValidation authorizationValidation) {
        if (authorizationValidation == null)
            return null;

        return authorizationValidation.role();
    }

    private AuthorizationValidation hasAuthorizationValidation(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            AuthorizationValidation av = handlerMethod.getMethod().getAnnotation(AuthorizationValidation.class);

            if (av == null) {
                av = handlerMethod.getMethod().getDeclaringClass().getAnnotation(AuthorizationValidation.class);
            }

            return av;
        }
        return null;
    }
}
