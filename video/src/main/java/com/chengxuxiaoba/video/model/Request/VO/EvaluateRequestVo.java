package com.chengxuxiaoba.video.model.Request.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateRequestVo {
    private Integer videoId;
    private String content;
    private Integer stars;

}
