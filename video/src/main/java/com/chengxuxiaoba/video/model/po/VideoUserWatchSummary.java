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
public class VideoUserWatchSummary {
    private Integer Id;
    private Integer accountId;
    private Integer watchVideoCount;
    private Date updateDateTime;
}
