package com.chengxuxiaoba.video.config;

import com.chengxuxiaoba.video.interceptor.AuthorizationInterceptor;
import com.chengxuxiaoba.video.interceptor.VideoWatchingInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

public class VideoWatchingInterceptorConfig  extends WebMvcConfigurationSupport {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(videoWatchingInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public VideoWatchingInterceptor videoWatchingInterceptor() {
        return new VideoWatchingInterceptor();
    }
}
