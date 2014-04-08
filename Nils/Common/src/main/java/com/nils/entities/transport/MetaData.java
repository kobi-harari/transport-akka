package com.nils.entities.transport;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class MetaData implements Serializable {

    final String trackingId;

    public MetaData() {
        trackingId = UUID.randomUUID().toString();
    }

    public MetaData(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getTrackingId() {
        return trackingId;
    }

}
