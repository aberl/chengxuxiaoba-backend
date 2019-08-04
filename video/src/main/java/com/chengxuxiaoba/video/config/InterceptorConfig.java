package com.chengxuxiaoba.video.config;

import com.chengxuxiaoba.video.interceptor.AuthorizationInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "authorization")
public class InterceptorConfig extends WebMvcConfigurationSupport {

    private List<String> excludeurls;

    public List<String> getExcludeurls() {
        return excludeurls;
    }

    public void setExcludeurls(List<String> excludeurls) {
        this.excludeurls = excludeurls;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludeurls);
    }

    @Bean
    public AuthorizationInterceptor authenticationInterceptor() {
        return new AuthorizationInterceptor();
    }
}
