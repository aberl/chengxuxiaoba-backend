package com.chengxuxiaoba.video.model.Response.VO;

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
public class CourseModuleResponseVo {
    private Integer id;
    private Integer courseId;
    private String name;
    private String courseName;
    private String description;
    private List<UploadFileResponseVo> imageList;
    private Integer status;
    private String statusDesc;
    private Integer videoCount;
    private Integer totalViewCount;
    private Integer totalPraiseCount;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private Date createDateTime;

    private List<String> aliImageUrls;
}
