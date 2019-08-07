package com.chengxuxiaoba.video.model.Response.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseVo {
    private Integer id;
    private String name;
    private String headerImg;
    private String mobilePhoneNo;
    private String wechatAccount;
    private String wechatHeaderImg;
    private Integer status;
    private String statusDesc;
    private String role;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date vipStartDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date vipEndDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Boolean isOverDue;

    private String token;

    private Map<String,Boolean> permissions;

    private Date createDateTime;
}
