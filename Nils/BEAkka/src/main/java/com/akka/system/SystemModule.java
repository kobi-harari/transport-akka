package com.akka.system;

import com.akka.db.GsonTranslator;
import com.akka.db.UserDbTemplate;
import com.akka.impl.BEUserBusinessLogic;
import com.akka.db.DbTemplate;
import com.akka.impl.IUserOperation;
import com.akka.impl.SMSSendService;
import com.akka.impl.SimpleDiscoveryService;
import com.akka.interfaces.*;
import com.couchbase.client.CouchbaseClient;
import com.google.inject.*;
import com.nils.entities.BaseEntity;
import com.nils.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kobi on 4/3/14.
 */
public class SystemModule implements Module {
    Logger logger = LoggerFactory.getLogger(SystemModule.class);

    @Override
    public void configure(Binder binder) {
        logger.debug("in configure before injections");
        binder.bind(ISendMessage.class).to(SMSSendService.class);
        binder.bind(IUserOperation.class).to(UserDbTemplate.class);
        binder.bind(IBEUserBusinessLogic.class).to(BEUserBusinessLogic.class);
        binder.bind(IJsonTranslator.class).to(GsonTranslator.class);
    }

    @Provides
    CouchbaseClient provideCouchbaseClient() {
        logger.debug("in provideCouchbaseClient");
        CouchbaseClient client = null;
        try {
            List<URI> uris = getUris();
            return new CouchbaseClient(uris, "default", "");
        } catch (IOException e) {
            logger.error("could not init couchbase client", e);
        }
        return client;
    }

    private List<URI> getUris() {
        logger.debug("get the urls for the server from simple discovery service");
        IDiscoveryService discoveryService = new SimpleDiscoveryService();
        List<ServiceDefinition> serviceDefinitions = discoveryService.getServiceDefinitions("db");
        List<URI> uriList = new LinkedList<>();
        for (ServiceDefinition definition : serviceDefinitions) {
            uriList.add(URI.create("http://" + definition.getHost() + ":" + definition.getPort()+"/pools"));
        }
        return uriList;
    }

}
