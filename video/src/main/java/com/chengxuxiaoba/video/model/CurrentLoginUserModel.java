package com.chengxuxiaoba.video.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentLoginUserModel {
    private String userId;
    private String name;
    private String wechat_account;
    private String status;
    private String role;
    private String mobilePhone;
    private Boolean isOverDue;
}
