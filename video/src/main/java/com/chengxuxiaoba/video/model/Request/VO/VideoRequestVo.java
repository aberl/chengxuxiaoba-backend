package com.chengxuxiaoba.video.model.Request.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoRequestVo {
    private Integer id;
    private Integer courseModuleId;
    private String name;
    private String attachments;
    private String duration;
    private String description;
    private String images;
    private Integer viewCount;
    private Integer praiseCount;
    private Integer status;
    private String aliVideoId;
}
