package com.akka.system;

import com.akka.impl.BEUserBusinessLogic;
import com.akka.impl.DbTemplate;
import com.akka.impl.SMSSendService;
import com.akka.interfaces.DbOperations;
import com.akka.interfaces.IBEUserBusinessLogic;
import com.akka.interfaces.ISendMessage;
import com.couchbase.client.CouchbaseClient;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kobi on 4/3/14.
 */
public class SystemModule implements Module {
    @Override
    public void configure(Binder binder) {
          binder.bind(ISendMessage.class).to(SMSSendService.class);
          binder.bind(DbOperations.class).to(DbTemplate.class);
          binder.bind(IBEUserBusinessLogic.class).to(BEUserBusinessLogic.class);
    }

    @Provides
    CouchbaseClient getCou(){
        CouchbaseClient client = null;
        try {
            List<URI> uris = new ArrayList<>(1);
            return new CouchbaseClient(uris,"default","");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return client;
    }

}
