package com.nils.entities.transport;

import com.nils.entities.BaseEntity;

import java.util.List;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class GetResponse extends Response {
    List<BaseEntity> entities;

    public GetResponse(List<BaseEntity> entities) {
        this.entities = entities;
    }

    public List<BaseEntity> getEntities() {
        return entities;
    }

}
