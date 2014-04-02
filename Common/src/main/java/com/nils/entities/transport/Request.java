package com.nils.entities.transport;

import java.io.Serializable;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class Request implements Serializable {
    MetaData metaData;
    String service;
    String action;
    Serializable message;

    public Request(MetaData metaData, String service, String action, Serializable message) {
        this.metaData = metaData;
        this.service = service;
        this.action = action;
        this.message = message;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public String getService() {
        return service;
    }

    public String getAction() {
        return action;
    }

    public Serializable getMessage() {
        return message;
    }
}
