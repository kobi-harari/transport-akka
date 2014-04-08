package com.akka.db;

import com.akka.impl.IUserOperation;
import com.akka.interfaces.IJsonTranslator;
import com.couchbase.client.CouchbaseClient;
import com.google.inject.Inject;

/**
 * Created by kobi on 4/8/14.
 */
public class UserDbTemplate<User,String> extends DbTemplate implements IUserOperation
{
    @Inject
    public UserDbTemplate(CouchbaseClient client, IJsonTranslator jsonTranslator) {
        super(client, jsonTranslator);
    }
}
