package com.akka.db;

import com.akka.interfaces.IJsonTranslator;
import com.couchbase.client.CouchbaseClient;
import com.google.inject.Inject;
import com.akka.interfaces.DbOperations;
import com.nils.entities.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kobi on 4/5/14.
 */
public class DbTemplate<T extends BaseEntity, ID extends Serializable> implements DbOperations {
    private Logger logger = LoggerFactory.getLogger(DbTemplate.class);
    protected CouchbaseClient client = null;
    protected IJsonTranslator jsonTranslator;

    public DbTemplate(CouchbaseClient client, IJsonTranslator jsonTranslator) {
        logger.info("creating new dbTemplate");
        this.client = client;
        this.jsonTranslator = jsonTranslator;
    }


    @Override
    public boolean exists(Serializable id) {
        return client.get(id.toString()) != null;
    }

    @Override
    public List<T> findByProperties(Map properties) {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> find(List list) {
        logger.debug("in find with list of id's");
        Map<String, Object> mapResult = client.getBulk(list);
        List<T> result = new ArrayList<>(mapResult.size());
        for (String key : mapResult.keySet()) {
            result.add((T) (this.jsonTranslator.decode(mapResult.get(key), BaseEntity.class)));
        }
        return result;
    }

    @Override
    public T find(Serializable id) {
        logger.debug("in find with id {}", id);
        return (T) jsonTranslator.decode(client.get(id.toString()), BaseEntity.class);
    }

    @Override
    public void delete(List list) {
        logger.debug("in delete with list of id's ");
        for (Object id : list) {
            client.delete(id.toString());
        }
    }

    @Override
    public void delete(Serializable id) {
        logger.debug("in delete with id ");
        client.delete(id.toString());
    }

    @Override
    public void save(List entities) {
        logger.debug("in save with list of entities");
        for (Object entity : entities) {
            String key = ((BaseEntity) entity).getId();
            client.set(key, jsonTranslator.encode(entity));
        }
    }

    @Override
    public void save(BaseEntity entity) {
        logger.debug("in save with one entity");
        String key = entity.getId();
        client.set(key, jsonTranslator.encode(entity));
    }

    @Override
    public void update(List entities) {
        logger.debug("in update with list of entities");
        for (Object entity : entities) {
            String key = ((BaseEntity) entity).getId();
            client.replace(key, jsonTranslator.encode(entity));
        }
    }

    @Override
    public void update(BaseEntity entity) {
        logger.debug("in update with one entity");
        String key = entity.getId();
        client.replace(key, jsonTranslator.encode(entity));
    }


}
