package com.nils.entities.transport;

import java.io.Serializable;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class Request implements Serializable {
    final MetaData metaData;
    final String service;
    final Action action;
    final Serializable message;

    public Request(MetaData metaData, String service, Action action, Serializable message) {
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

    public Action getAction() {
        return action;
    }

    public Serializable getMessage() {
        return message;
    }

    static public enum Action {
        GET, SAVE, DELETE, UPDATE, FIND_BY_PROPERTY;
    }

    @Override
    public String toString() {
        return "Request{" +
                "metaData=" + metaData +
                ", service='" + service + '\'' +
                ", action=" + action +
                ", message=" + message +
                '}';
    }
}
