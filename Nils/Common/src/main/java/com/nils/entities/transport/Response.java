package com.nils.entities.transport;

import com.nils.entities.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class Response implements Serializable{
    MetaData metaData;
    String service;
    Request.Action action;
    Serializable message;

    public Response(MetaData metaData, String service, Request.Action action){
        this(metaData, service, action, null);
    }

    public Response(MetaData metaData, String service, Request.Action action, Serializable message) {
        this.metaData = metaData;
        this.service = service;
        this.action = action;
        this.message = message;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Request.Action getAction() {
        return action;
    }

    public void setAction(Request.Action action) {
        this.action = action;
    }

    public Serializable getMessage() {
        return message;
    }

    public void setMessage(Serializable message) {
        this.message = message;
    }
}
