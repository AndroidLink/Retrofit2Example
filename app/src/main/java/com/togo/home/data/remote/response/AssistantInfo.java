package com.togo.home.data.remote.response;


import java.io.Serializable;

/**
 * Created by hetao on 16/2/26.
 */
public class AssistantInfo implements Serializable {
    private static final long serialVersionUID = -5504365222891340145L;

    private String xbkp_name;
    private String xbkp_id;

    public void setXbkp_name(String xbkpName) {
        this.xbkp_name = xbkpName;
    }

    public void setXbkp_id(String xbkpId) {
        this.xbkp_id = xbkpId;
    }

    public String getXbkp_name() {
        return xbkp_name;
    }

    public String getXbkp_id() {
        return xbkp_id;
    }
}
