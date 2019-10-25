package com.chengxuxiaoba.video.model.ali;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoPlayAuth extends VideoBasePlayInfo{
    private String playAuth;

    @Builder
    private VideoPlayAuth(String videoId, String duration,String cover,String title,String playAuth)
    {
        super(videoId, duration,cover,title);
        this.playAuth=playAuth;
    }
}
