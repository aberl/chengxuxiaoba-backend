package com.chengxuxiaoba.video.model;

import com.chengxuxiaoba.video.constant.ValidationCodeCategory;

public class ValidationCode {
    private String mobilePhoneNo;
    private ValidationCodeCategory category;

    public String getMobilePhoneNo() {
        return mobilePhoneNo;
    }

    public void setMobilePhoneNo(String mobilePhoneNo) {
        this.mobilePhoneNo = mobilePhoneNo;
    }

    public ValidationCodeCategory getCategory() {
        return category;
    }

    public void setCategory(ValidationCodeCategory category) {
        this.category = category;
    }
}
