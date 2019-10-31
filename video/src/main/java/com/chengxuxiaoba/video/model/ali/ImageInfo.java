package com.chengxuxiaoba.video.model.ali;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageInfo {
    private String imageId;
    private String title;
    private String fileURL;
    private String url;
    private String creationTime;
}
