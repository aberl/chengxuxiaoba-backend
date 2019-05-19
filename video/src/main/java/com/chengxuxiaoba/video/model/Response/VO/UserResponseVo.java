package com.chengxuxiaoba.video.model.Response.VO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Map;
import java.util.List;

public class UserResponseVo {
    private Integer id;
    private String name;
    private String headerImg;
    private String mobilePhoneNo;
    private String wechatAccount;
    private String wechatHeaderImg;
    private Integer status;
    private String statusDesc;
    private List<String> roles;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date vipStartDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date vipEndDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createDateTime;

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

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Date getVipStartDate() {
        return vipStartDate;
    }

    public void setVipStartDate(Date vipStartDate) {
        this.vipStartDate = vipStartDate;
    }

    public Date getVipEndDate() {
        return vipEndDate;
    }

    public void setVipEndDate(Date vipEndDate) {
        this.vipEndDate = vipEndDate;
    }
}
