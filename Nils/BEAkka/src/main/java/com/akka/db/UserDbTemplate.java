package com.akka.db;

import com.akka.interfaces.IJsonTranslator;
import com.akka.interfaces.IUserOperation;
import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.*;
import com.google.inject.Inject;
import com.nils.entities.BaseEntity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kobi on 4/8/14.
 */
public class UserDbTemplate<User, String> extends DbTemplate implements IUserOperation {
    @Inject
    public UserDbTemplate(CouchbaseClient client, IJsonTranslator jsonTranslator) {
        super(client, jsonTranslator);
    }

    //todo - move this to dbTemplate
    @Override
    public List<User> findByProperties(Map properties) {
        Query query = new Query();
        query.setIncludeDocs(true);

        Set keys = properties.keySet();
        View view = null;
        for (Object key : keys) {
            Object searchCriteria = properties.get(key);
            query.setKey(ComplexKey.of(searchCriteria));
            view = client.getView("user", key.toString());
//            View view = client.getView("user", "userByAccountId");

        }
        List<User> users = new LinkedList<>();
        if (view != null) {
            ViewResponse rows = client.query(view, query);
            for (ViewRow row : rows) {
                users.add((User) jsonTranslator.decode(row.getDocument(), BaseEntity.class));
            }
        }
        return users;
    }

}
