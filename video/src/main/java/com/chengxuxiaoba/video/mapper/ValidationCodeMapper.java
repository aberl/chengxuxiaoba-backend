package com.chengxuxiaoba.video.mapper;

import com.chengxuxiaoba.video.model.po.ValidationCode;
import org.apache.ibatis.annotations.Param;

public interface ValidationCodeMapper {
   public Integer insert(ValidationCode validationCode);

   public ValidationCode getEffectiveCode(@Param("mobilePhoneNo") String mobilePhoneNo, @Param("codeCategory") String codeCategory);

   public Integer invalidateCode(@Param("mobilePhoneNo") String mobilePhoneNo, @Param("codeCategory") String codeCategory, @Param("code") String code);

   Integer getSendMsgCountToday(@Param("mobilePhoneNo") String mobilePhoneNo);
}
