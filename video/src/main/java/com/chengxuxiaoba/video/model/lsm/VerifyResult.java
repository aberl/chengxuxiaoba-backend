package com.chengxuxiaoba.video.model.lsm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyResult {
    private Integer error;
    private String res;

    public Boolean isSuccess() {
        return "success".equalsIgnoreCase(this.getRes());
    }
}
