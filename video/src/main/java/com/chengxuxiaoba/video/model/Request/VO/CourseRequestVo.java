package com.chengxuxiaoba.video.model.Request.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestVo {
    private Integer id;
    private String name;
    private String description;
    private String aliImgUrls;
    private String images;
    private Integer status;
}
