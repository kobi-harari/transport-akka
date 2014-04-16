package com.akka.db;

import com.akka.interfaces.IJsonTranslator;
import com.akka.interfaces.IOrderItemOperation;
import com.couchbase.client.CouchbaseClient;
import com.google.inject.Inject;

/**
 * Created by kobi on 4/16/14.
 */
public class OrderItemDbTemplate <OrderItem,String> extends DbTemplate implements IOrderItemOperation {
    @Inject
    public OrderItemDbTemplate(CouchbaseClient client, IJsonTranslator jsonTranslator) {
        super(client, jsonTranslator);
    }
}
