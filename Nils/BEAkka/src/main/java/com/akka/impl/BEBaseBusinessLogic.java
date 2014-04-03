package com.akka.impl;

import com.nils.interfaces.IBaseBusinessLogic;

import java.io.Serializable;
import java.util.List;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class BEBaseBusinessLogic<T,ID extends Serializable> implements IBaseBusinessLogic<T, ID> {


    @Override
    public boolean exists(List<ID> ids) {
        return false;
    }

    @Override
    public List<T> find(List<ID> ids) {
        return null;
    }

    @Override
    public void delete(List<ID> ids) {

    }

    @Override
    public void save(List<T> entities) {

    }

    @Override
    public void update(List<T> entities) {

    }
}
