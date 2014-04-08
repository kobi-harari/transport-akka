package com.akka.impl;

import com.akka.interfaces.IDiscoveryService;
import com.akka.system.ServiceDefinition;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Simple implementation of discovery service
 * Created by kobi on 4/7/14.
 */
public class SimpleDiscoveryService implements IDiscoveryService {
    Logger logger = LoggerFactory.getLogger(SimpleDiscoveryService.class);
    Map<String, List<ServiceDefinition>> definitionMap;
    PropertiesConfiguration configuration= null;

    public SimpleDiscoveryService() {
        logger.info("create a new SimpleDiscoveryService");
        Map<String, List<ServiceDefinition>> definitionMap = new LinkedHashMap<>();
        try {
            logger.info("reading the discovery information from a file");
            configuration = new PropertiesConfiguration("discovery.propertise");
        } catch (ConfigurationException e) {
            logger.error("Could not read configuration file", e);
        }
        Iterator<String> dbKeys = configuration.getKeys("db");
        List<ServiceDefinition> dbServiceDefinitions = new LinkedList<>();
        String[] hostValues = {};
        String[] portValues = {};
        while (dbKeys.hasNext()){
            String key = dbKeys.next();
            if(key.contains("host")){
                hostValues = configuration.getStringArray(key);
            }else if(key.contains("port")){
                portValues = configuration.getStringArray(key);
            }
        }
        for (int i = 0; i < hostValues.length; i++) {
            ServiceDefinition definition = new ServiceDefinition("couchbase", hostValues[i], portValues[i]);
            dbServiceDefinitions.add(definition);
        }
        definitionMap.put("db",dbServiceDefinitions);
        this.definitionMap = definitionMap;

    }


    @Override
    public List<ServiceDefinition> getServiceDefinitions(String serviceName) {
        return definitionMap.get(serviceName);
    }
}
