package com.akka.impl;

import com.nils.interfaces.IBaseBusinessLogic;

import java.io.Serializable;
import java.util.List;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class BEBaseBusinessLogic<T,ID extends Serializable> implements IBaseBusinessLogic {

    @Override
    public boolean exists(List ids) {
        return false;
    }

    @Override
    public Object findById(Serializable serializable) {
        return null;
    }

    @Override
    public List findByIds(List list) {
        return null;
    }

    @Override
    public void delete(Serializable serializable) {

    }

    @Override
    public void delete(List list) {

    }

    @Override
    public void save(Object entity) {

    }

    @Override
    public void save(List entities) {

    }

    @Override
    public void update(Object entity) {

    }

    @Override
    public void update(List entities) {

    }
}
