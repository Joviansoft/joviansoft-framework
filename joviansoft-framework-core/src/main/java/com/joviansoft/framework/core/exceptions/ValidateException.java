package com.joviansoft.framework.core.exceptions;

/**
 * Created by bigbao on 14-2-20.
 * 参数校验异常
 */
public class ValidateException extends BaseException {

    private static final long serialVersionUID = 1910956241207662329L;

    public ValidateException(String message) {
        super(message, ServiceErrorCode.INVALID_ARGUMENT);
    }
}
