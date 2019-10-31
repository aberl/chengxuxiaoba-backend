package com.chengxuxiaoba.video.model.Request.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseModuleRequestVo {
    private Integer id;
    private Integer courseId;
    private String name;
    private String courseName;
    private String description;
    private String aliImgUrls;
    private String images;
    private Integer status;

}
