package com.nils.utils;

import com.google.gson.*;
import com.nils.entities.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by kobi on 4/7/14.
 */
public class GsonTranslator implements IJsonTranslator {
    private static final Logger logger = LoggerFactory.getLogger(GsonTranslator.class);
    private Gson gson;

    public GsonTranslator() {
        logger.info("initiate a new GsonCoubaseTranslator");

        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
                return json == null ? null : new Date(json.getAsLong());
            }
        });
        builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext
                    context) {
                return src == null ? null : new JsonPrimitive(src.getTime());
            }
        });
        this.gson = builder.create();
    }

    @Override
    public Object encode(Object source) {
        return this.gson.toJson(source);
    }

    @Override
    public Object decode(Object source, Class clazz) {
        return this.gson.fromJson(source.toString(), BaseEntity.class);
    }
}
