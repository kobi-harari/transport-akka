package com.akka.impl;

import com.couchbase.client.CouchbaseClient;
import com.google.inject.Inject;
import com.akka.interfaces.DbOperations;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kobi on 4/5/14.
 */
public class DbTemplate implements DbOperations {

    private final CouchbaseClient client;

    @Inject
    public DbTemplate(CouchbaseClient client) {
        this.client = client;
    }

    @Override
    public boolean exists(Serializable  id) {
        return client.get(id.toString()) != null;
    }

    @Override
    public List find(List list) {
        return null;
    }

    @Override
    public Object find(Serializable serializable) {
        return null;
    }

    @Override
    public void delete(List list) {

    }

    @Override
    public void delete(Serializable serializable) {

    }

    @Override
    public void save(List entities) {

    }

    @Override
    public void save(Object entityy) {

    }

    @Override
    public void update(List entities) {

    }

    @Override
    public void update(Object entity) {

    }
}
