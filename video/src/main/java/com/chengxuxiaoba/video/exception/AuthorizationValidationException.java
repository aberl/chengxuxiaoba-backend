package com.chengxuxiaoba.video.exception;

import com.chengxuxiaoba.video.model.ResultCode;
import com.chengxuxiaoba.video.model.ResultMessage;

public class AuthorizationValidationException extends BaseException {
    public AuthorizationValidationException() {
        super(ResultCode.NoAuthotization, ResultMessage.NOAUTHORIZATION);
    }
}
