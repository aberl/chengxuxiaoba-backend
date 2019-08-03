package com.chengxuxiaoba.video.model.Request.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequestVo {
    private Integer videoId;
    private String name;
    private String content;
}
