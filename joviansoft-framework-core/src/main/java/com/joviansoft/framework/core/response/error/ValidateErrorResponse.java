package com.joviansoft.framework.core.response.error;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bigbao on 14-2-21.
 * 参数校验错误返回
 */
public class ValidateErrorResponse extends ErrorResponse{
    public ValidateErrorResponse(int code) {
        super(code);
    }
    @JsonProperty("sub_msg")
    private String subMessage;
    public String getSubMessage() {
        return subMessage;
    }

    public void setSubMessage(String subMessage) {
        this.subMessage = subMessage;
    }

}
