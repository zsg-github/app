package com.paas.app.exception;

import com.paas.app.enums.ErrorsEnum;

import java.io.Serializable;

/**
 * 功能描述：
 * 本类是非受检异常的基类，所有需要隐藏受检异的处常理逻辑异常类，需要继承本类
 * 上层代码最好使用Exception统一捕获非受检异常类
 * 非受检异常类统一错误码：1001：系统内部错误
 * @Author 张书根
 */
public class BaseRunTimeException extends RuntimeException implements Serializable {


    private static final long serialVersionUID = 9126175652440320111L;

    private String errCode;
    private String message;

    public BaseRunTimeException(String errCode) {
        this.errCode = errCode;
    }

    public BaseRunTimeException(String errCode, String message) {
        super(message);
        this.errCode = errCode;
        this.message = message;
    }

    public BaseRunTimeException(ErrorsEnum errorsEnum) {
        this(String.valueOf(errorsEnum.getErrorCode()), errorsEnum.getMessage());
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
