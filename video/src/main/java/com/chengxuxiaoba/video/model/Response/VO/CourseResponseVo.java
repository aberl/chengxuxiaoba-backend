package com.chengxuxiaoba.video.model.Response.VO;

import com.chengxuxiaoba.video.model.ali.ImageInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseVo {
    private Integer id;
    private String name;
    private String description;
    // private String images;
    private List<UploadFileResponseVo> imageList;
    private Integer status;
    private String statusDesc;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date createDateTime;

    private List<String> aliImageUrls;
}
