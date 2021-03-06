package com.chengxuxiaoba.video.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentLoginUserModel {
    private Integer userId;
    private String name;
    private String wechat_account;
    private Integer status;
    private Integer role;
    private String mobilePhone;
    private Boolean isOverDue;

    private Map<String,Boolean> permissions;
}
