package com.joviansoft.mpolice.response;

import com.joviansoft.gps.domain.Message;

/**
 * Created by bigbao on 14-3-11.
 */
public class GpsResponse extends JovianResponse{
    private Message gpsBean;

    public Message getGpsBean() {
        return gpsBean;
    }

    public void setGpsBean(Message gpsBean) {
        this.gpsBean = gpsBean;
    }
}
