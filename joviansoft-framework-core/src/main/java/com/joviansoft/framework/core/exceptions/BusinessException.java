package com.joviansoft.framework.core.exceptions;

/**
 * Created by bigbao on 14-2-26.
 */
public class BusinessException extends BaseException{

    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
