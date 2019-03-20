package com.chengxuxiaoba.video.model.Request.VO;

import com.chengxuxiaoba.video.constant.ValidationCodeCategory;

public class ValidationCodeRequestVo {
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
