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
    private String title;
    private String playAuth;
    private String playUrl;
    private String cover;
    private String duration;
}
