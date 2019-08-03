package com.chengxuxiaoba.video.model.Response.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponseVo {
    private Integer id;
    private Integer issueId;
    private String content;
    private Integer answererId;
    private Integer status;
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private Date createDateTime;

    private UserResponseVo userResponseVo;
}
