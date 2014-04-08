package com.akka.db;

import com.akka.interfaces.IAccountOperation;
import com.akka.interfaces.IJsonTranslator;
import com.couchbase.client.CouchbaseClient;
import com.google.inject.Inject;

/**
 * Created by kobi on 4/8/14.
 */
public class AccountDbTemplate<Account,String> extends DbTemplate implements IAccountOperation {

    @Inject
    public AccountDbTemplate(CouchbaseClient client, IJsonTranslator jsonTranslator) {
        super(client, jsonTranslator);
    }
}
