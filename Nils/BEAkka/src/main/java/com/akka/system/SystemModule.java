package com.akka.system;

import com.akka.db.AccountDbTemplate;
import com.akka.db.OrderDbTemplate;
import com.akka.db.OrderItemDbTemplate;
import com.akka.db.UserDbTemplate;
import com.akka.impl.*;
import com.akka.interfaces.*;
import com.couchbase.client.CouchbaseClient;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
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
        binder.bind(IAccountOperation.class).to(AccountDbTemplate.class);
        binder.bind(IOrderOperation.class).to(OrderDbTemplate.class);
        binder.bind(IOrderItemOperation.class).to(OrderItemDbTemplate.class);
        binder.bind(IBEAccountBusinessLogic.class).to(BEAccountBusinessLogic.class);
        binder.bind(IBEUserBusinessLogic.class).to(BEUserBusinessLogic.class);
        binder.bind(IBEOrderBusinessLogic.class).to(BEOrderBusinessLogic.class);
        binder.bind(IBEOrderItemBusinessLogic.class).to(BEOrderItemBusinessLogic.class);
        binder.bind(IJsonTranslator.class).to(JacksonTranslator.class);
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
