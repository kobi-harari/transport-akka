package com.akka.db;

import com.akka.interfaces.IJsonTranslator;
import com.akka.interfaces.IOrderOperation;
import com.couchbase.client.CouchbaseClient;
import com.google.inject.Inject;

/**
 * Created by kobi on 4/16/14.
 */
public class OrderDbTemplate <Order,String> extends DbTemplate implements IOrderOperation {
    @Inject
    public OrderDbTemplate(CouchbaseClient client, IJsonTranslator jsonTranslator) {
        super(client, jsonTranslator);
    }
}
