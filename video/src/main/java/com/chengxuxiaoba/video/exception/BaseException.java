package com.chengxuxiaoba.video.exception;

public class BaseException extends Exception {
    private int code;

    public BaseException(int messageCode, String message){
        super(message);
        this.code=messageCode;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
