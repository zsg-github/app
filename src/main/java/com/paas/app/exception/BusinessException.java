package com.paas.app.exception;


import com.paas.app.enums.ErrorsEnum;

public class BusinessException extends BaseException {

    public BusinessException(String errCode) {
        super(errCode);
    }

    public BusinessException(String errCode, String message) {
        super(errCode, message);
    }

    /**
     * 功能描述：执行本类异常抛出，本方法需要传入ErrorsEnum对象实例
     * 这个方法是通用方法，可以通过在ErrorsEnum对象中进行各种错误定义，来进行各种错误信息的统一管理
     * @param errorsEnum
     * @Author 张书根
     *
     */
    public static void throwMessage(ErrorsEnum errorsEnum) throws BusinessException {
        String errorCode = String.valueOf(errorsEnum.getErrorCode());
        String message = errorsEnum.getMessage();
        throw new BusinessException(errorCode,message);
    }

    /**
     * 功能描述: 执行本类异常抛出，本方法需要传入字符串和异常消息描述
     * @param errCode
     * @param message
     * @Author 张书根
     */
    public static void throwMessage(String errCode, String message) throws BusinessException {
        throw new BusinessException(errCode,message);
    }
}
