package com.akka.impl;

import com.akka.interfaces.DbOperations;
import com.google.inject.Inject;
import com.nils.interfaces.IBaseBusinessLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by uri.silberstein on 4/3/14.
 * For the be side
 */
@SuppressWarnings("unchecked")
public class BEBaseBusinessLogic<T,ID extends Serializable> implements IBaseBusinessLogic<T, ID> {
    private static final Logger logger = LoggerFactory.getLogger(BEBaseBusinessLogic.class);
    protected DbOperations dbOperations;

    @Inject
    public BEBaseBusinessLogic(DbOperations dbOperations) {
        logger.info("inject the dbOperation to the base business logic");
        this.dbOperations = dbOperations;
    }

    @Override
    public boolean exists(Serializable id) {
        logger.debug("check if this obejct already exist {}",id);
        return dbOperations.exists(id);
    }

    @Override
    public List<T> find(List<ID> ids) {
        logger.debug("find in base in business logic");
        return dbOperations.find(ids);
    }

    @Override
    public void delete(List<ID> ids) {
        logger.debug("delete in base in business logic");
        dbOperations.delete(ids);
    }

    @Override
    public void save(List<T> entities) {
        logger.debug("save in base in business logic");
        dbOperations.save(entities);
    }

    @Override
    public void update(List<T> entities) {
        logger.debug("update in base in business logic");
        dbOperations.update(entities);
    }
}
