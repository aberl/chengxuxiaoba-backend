package com.chengxuxiaoba.video.model.ali;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoPlayInfo extends VideoBasePlayInfo {
    private String playURL;

    @Builder
    private VideoPlayInfo(String videoId, String duration,String cover,String title,String playURL)
    {
        super(videoId, duration,cover,title);
        this.playURL=playURL;
    }
}
