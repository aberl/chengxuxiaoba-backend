package com.chengxuxiaoba.video.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseModule {
    private Integer id;
    private Integer courseId;
    private String courseName;
    private String name;
    private String description;
    private String images;
    private String aliImgUrls;
    private Integer status;
    private Date createDateTime;
}
