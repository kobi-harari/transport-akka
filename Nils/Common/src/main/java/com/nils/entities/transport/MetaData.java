package com.nils.entities.transport;

import akka.actor.ActorRef;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class MetaData implements Serializable {

    final String trackingId;
    ActorRef originalSender;

    public MetaData() {
        trackingId = UUID.randomUUID().toString();
    }

    public MetaData(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public ActorRef getOriginalSender() {
        return originalSender;
    }

    public void setOriginalSender(ActorRef originalSender) {
        this.originalSender = originalSender;
    }
}
