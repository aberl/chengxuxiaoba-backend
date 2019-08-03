package com.chengxuxiaoba.video.model.Request.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestVo {
    private String name;
    private String content;
    private Integer category;
    private Boolean broadcast;
    private List<Integer> messageIdList;

}
