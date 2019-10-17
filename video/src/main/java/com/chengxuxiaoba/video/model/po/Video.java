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
public class Video {
    private Integer id;
    private Integer courseModuleId;
    private String file;
    private String name;
    private String attachments;
    private String duration;
    private String description;
    private String images;
    private Integer viewCount;
    private Integer praiseCount;
    private Integer status;
    private Date createDateTime;
    private String aliVideoId;
}
