package com.joviansoft.framework.core.response.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joviansoft.framework.core.config.ErrorConfig;

import java.io.Serializable;

/**
 * Created by bigbao on 14-2-21.
 */
public class ErrorResponse implements Serializable{

    private static final long serialVersionUID = -1808981221609910125L;
    @JsonProperty("error_code")
    private int code;
    @JsonProperty("error_msg")
    private String message;

    public ErrorResponse(int code) {
        this.code = code;
        getErrorMessageFromConfig();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
        getErrorMessageFromConfig();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private void getErrorMessageFromConfig(){
        this.message = ErrorConfig.getErrorMessage(code + "");
    }
//    public void appendMessage(String message){
//        this.message = " "+message;
//    }
}
