package com.joviansoft.framework.core.exceptions;

/**
 * Created by bigbao on 14-2-21.
 */
public class SystemServiceException extends BaseException {

    private static final long serialVersionUID = 456973335454685630L;

    public SystemServiceException(ErrorCode errorCode) {
       super(errorCode);
    }
}
