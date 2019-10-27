package com.chengxuxiaoba.video.interceptor;

import com.chengxuxiaoba.video.annotation.VideoWatchingPermissionValidation;
import com.chengxuxiaoba.video.constant.PERMISSION;
import com.chengxuxiaoba.video.exception.VideoWatchingCountSurpassException;
import com.chengxuxiaoba.video.model.CurrentLoginUserModel;
import com.chengxuxiaoba.video.service.IVideoService;
import com.chengxuxiaoba.video.service.imp.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

public class VideoWatchingInterceptor implements HandlerInterceptor {
    @Autowired
    private AuthenticationService authenticationService;

    @Value("${authorization.videowatchinglimit}")
    private String videowatchinglimit;

    @Autowired
    private IVideoService videoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        VideoWatchingPermissionValidation videoWatchingPermissionValidation = hasVideoWatchingPermissionValidation(handler);

        if (videoWatchingPermissionValidation != null) {
            Integer videoWatchCount = 0;
            try {
                CurrentLoginUserModel currentLoginUserModel = authenticationService.getCurrentLoginUserModelFromRequest();

                if (currentLoginUserModel == null) {
                    return true;
                }

                Map<String, Boolean> permissionMap = currentLoginUserModel.getPermissions();

                if (permissionMap == null) {
                    return true;
                }

                Boolean videoWatchingLimitFlag = permissionMap.containsKey(PERMISSION.WATCHINGLIMITEDPERDAY) ?
                        permissionMap.get(PERMISSION.WATCHINGLIMITEDPERDAY) : false;

                if (!videoWatchingLimitFlag) {
                    return true;
                }

                videoWatchCount = videoService.getVideoWatchCountInOneDay(currentLoginUserModel.getUserId(), new Date());
            } catch (Exception ex) {

            }
            if (videoWatchCount > Integer.valueOf(videowatchinglimit)) {
                throw new VideoWatchingCountSurpassException();
            }
        }
        return true;
    }

    private VideoWatchingPermissionValidation hasVideoWatchingPermissionValidation(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            VideoWatchingPermissionValidation av = handlerMethod.getMethod().getAnnotation(VideoWatchingPermissionValidation.class);

            return av;
        }
        return null;
    }
}
