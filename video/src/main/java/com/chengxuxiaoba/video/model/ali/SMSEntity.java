package com.chengxuxiaoba.video.model.ali;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SMSEntity {
    private String phoneNumbers;
    private String signName;
    private String templateCode;
    private String templateParam;
}
