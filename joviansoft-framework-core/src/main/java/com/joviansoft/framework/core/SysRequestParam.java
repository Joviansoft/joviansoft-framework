package com.joviansoft.framework.core;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by bigbao on 14-2-21.
 */
public class SysRequestParam {
    @NotEmpty
    private String appKey;
    private String timeStamp;
    private String signed;
    private String session;

    public SysRequestParam() {
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSigned() {
        return signed;
    }

    public void setSigned(String signed) {
        this.signed = signed;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
