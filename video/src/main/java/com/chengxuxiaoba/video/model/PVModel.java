package com.chengxuxiaoba.video.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpSession;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PVModel {
    private String ip;
    private List<HttpSession> sessions;
    private String clientInfo;
}
