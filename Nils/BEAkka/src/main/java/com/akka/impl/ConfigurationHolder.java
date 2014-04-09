package com.akka.impl;

import com.akka.interfaces.IConfigurationHolder;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by kobi on 4/9/14.
 */
public class ConfigurationHolder implements IConfigurationHolder {
    Logger logger = LoggerFactory.getLogger(ConfigurationHolder.class);
    Map<String,String> configurationMap;

    public ConfigurationHolder() {
        this.configurationMap = new LinkedHashMap<>();
        try {
            PropertiesConfiguration configuration = new PropertiesConfiguration("configuration.properties");
            Iterator<String> keys = configuration.getKeys();
            while (keys.hasNext()){
                String key = keys.next();
                configurationMap.put(key, configuration.getString(key));
            }
        } catch (ConfigurationException e) {
            logger.error("could not read configuration",e);
        }
    }

    @Override
    public String getValue(String key) {
        logger.debug("called get value {}",key);
        return configurationMap.get(key);
    }
}
