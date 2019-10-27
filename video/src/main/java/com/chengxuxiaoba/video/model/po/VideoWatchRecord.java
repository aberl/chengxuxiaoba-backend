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
public class VideoWatchRecord {
    private Integer id;
    private Integer courseModuleId;
    private Integer videoId;
    private Integer accountId;
    private Date lastWatchTime;
}
