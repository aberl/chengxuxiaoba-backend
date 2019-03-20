package com.chengxuxiaoba.video.model.Response.VO;

import java.util.Date;

public class UserResponseVo {
    private String name;
    private String mobilePhoneNo;
    private String wechatAccount;
    private String wechatHeaderImg;
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilePhoneNo() {
        return mobilePhoneNo;
    }

    public void setMobilePhoneNo(String mobilePhoneNo) {
        this.mobilePhoneNo = mobilePhoneNo;
    }

    public String getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(String wechatAccount) {
        this.wechatAccount = wechatAccount;
    }

    public String getWechatHeaderImg() {
        return wechatHeaderImg;
    }

    public void setWechatHeaderImg(String wechatHeaderImg) {
        this.wechatHeaderImg = wechatHeaderImg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
