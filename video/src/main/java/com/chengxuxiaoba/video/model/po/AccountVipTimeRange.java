package com.chengxuxiaoba.video.model.po;

import java.util.Date;

public class AccountVipTimeRange {
    private Integer id;
    private Integer accountId;
    private Date startDate;
    private Date endDate;
    private Integer status;
    private Date createdateTime;
    private Date updateDateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedateTime() {
        return createdateTime;
    }

    public void setCreatedateTime(Date createdateTime) {
        this.createdateTime = createdateTime;
    }

    public Date getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Date updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
