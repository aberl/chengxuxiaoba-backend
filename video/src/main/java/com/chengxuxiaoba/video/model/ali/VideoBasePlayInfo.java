package com.chengxuxiaoba.video.model.ali;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoBasePlayInfo {
    private String videoId;
    private String duration;
    private String cover;
    private String title;
}
