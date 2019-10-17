package com.chengxuxiaoba.video.model.Response.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoResponseVo {
    private Integer id;
    private Integer courseModuleId;
    private UploadFileResponseVo video;
    private String name;
    private List<UploadFileResponseVo> attachmentList;
    private String duration;
    private String description;
    private String images;
    private Integer viewCount;
    private Integer praiseCount;
    private Integer status;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private Date createDateTime;
    private String statusDesc;
    private String aliVideoId;
    private AliVideoInfoResponseVo aliVideoInfo;

}
