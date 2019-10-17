package com.chengxuxiaoba.video.model.Response.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AliVideoInfoResponseVo {
    private String videoId;
    private String playURL;
    private String baseTitle;
    private String playAuth;
    private String metaTitle;
}
