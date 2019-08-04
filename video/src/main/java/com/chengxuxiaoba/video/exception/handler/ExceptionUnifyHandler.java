package com.chengxuxiaoba.video.exception.handler;

import com.chengxuxiaoba.video.exception.AuthorizationValidationException;
import com.chengxuxiaoba.video.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestController
@ControllerAdvice
@ResponseStatus(HttpStatus.OK)
public class ExceptionUnifyHandler {

    @ExceptionHandler(AuthorizationValidationException.class)
    public Result<Object> authorizationExceptionHandler(AuthorizationValidationException exception)
    {
        return Result.builder()
                .code(exception.getCode())
                .data(null)
                .message(exception.getMessage())
                .build();
    }
}
