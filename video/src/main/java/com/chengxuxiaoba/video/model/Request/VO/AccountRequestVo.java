package com.chengxuxiaoba.video.model.Request.VO;

import java.util.Date;
import java.util.List;

public class AccountRequestVo {
    private Integer id;
    private String name;
    private String headerImg;
    private String mobilePhoneNo;
    private String wechatAccount;
    private String wechatHeaderImg;
    private Integer status;
    private List<Integer> roleIdList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
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

    public List<Integer> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Integer> roleIdList) {
        this.roleIdList = roleIdList;
    }
}
