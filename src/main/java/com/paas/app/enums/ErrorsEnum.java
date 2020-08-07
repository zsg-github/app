package com.paas.app.enums;

/**
 * 可以在这里定义自己的错误类型
 */

public enum ErrorsEnum {

    SUCCESS(1000, "处理成功"),
    COMMON_ERROR(1001, "系统繁忙，请稍后再试"),
    OBJECT_NULL(1002, "对象为空"),
    PARAM_NULL(1003, "参数为空"),
    CREATE_ERROR(1004, "创建失败");


    private Integer errorCode;
    private String message;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ErrorsEnum(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
