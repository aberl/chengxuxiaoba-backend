package com.chengxuxiaoba.video.annotation;

import com.chengxuxiaoba.video.constant.RoleConstant;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface VideoWatchingPermissionValidation {
    String value() default "";
}
