package com.tuiyi.allin.core;

/**
 * 错误信息
 * @author liuhuijie
 * @date 2020/11/15
 */
public class AdError {
    protected int code;
    protected String message;

    public AdError(int code,String message){
        this.code=code;
        this.message=message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setDesc(String message) {
        this.message = message;
    }
}
