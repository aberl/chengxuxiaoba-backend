package com.chengxuxiaoba.video.exception;

import com.chengxuxiaoba.video.model.ResultCode;
import com.chengxuxiaoba.video.model.ResultMessage;

public class VideoWatchingCountSurpassException extends BaseException {
    public VideoWatchingCountSurpassException() {
        super(ResultCode.VideoWatchingCountSurpass, ResultMessage.VIDEOWATCHINGCOUNTSURPASS);
    }
}
