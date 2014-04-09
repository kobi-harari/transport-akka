package com.akka.impl;

import com.akka.interfaces.IJsonTranslator;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by kobi.harari on 4/9/2014.
 */
public class JacksonTranslator implements IJsonTranslator {
    private static final Logger logger = LoggerFactory.getLogger(JacksonTranslator.class);
    private ObjectMapper jacksonTranlator;

    public JacksonTranslator() {
        logger.info("Creating new Jackson translator");
        this.jacksonTranlator = new ObjectMapper();
        this.jacksonTranlator.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        this.jacksonTranlator.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Object encode(Object source) {
        try {
            return this.jacksonTranlator.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            logger.error("something wrong I can't translate the object to json string", e);
        }
        return null;
    }

    @Override
    public Object decode(Object source, Class clazz) {
        logger.debug("decode was called");
        try {
            return this.jacksonTranlator.readValue(source.toString(), clazz);
        } catch (IOException e) {
            logger.error("could not parse the object", e);
        }
        return null;
    }
}
