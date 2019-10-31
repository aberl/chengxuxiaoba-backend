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
public class Course {
    private Integer id;
    private String name;
    private String description;
    private String aliImgUrls;
    private String images;
    private Integer status;
    private Date createDateTime;
}
