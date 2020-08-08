package com.paas.app.exception;

/**
 * 功能描述：
 * 本类是系统受检性异常类的基类，所有业务侧的异常都将继承本类
 * 注意：本异常是受检异常，所以需要对本异常进行明确的捕获并处理
 * @Author 张书根
 */
public class BaseException extends Exception {
    private static final long serialVersionUID = 8915352339678421653L;
    private String errCode;

    private String message;

    public BaseException(String errCode){
        this.errCode = errCode;
    }

    public BaseException(String errCode, String message){
        this.errCode = errCode;
        this.message = message;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
