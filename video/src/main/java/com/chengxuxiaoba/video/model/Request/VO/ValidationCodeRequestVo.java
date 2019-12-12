package com.chengxuxiaoba.video.model.Request.VO;

import com.chengxuxiaoba.video.constant.ValidationCodeCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationCodeRequestVo {
    private String captcha;
    private String mobilePhoneNo;
    private ValidationCodeCategory category;
}
