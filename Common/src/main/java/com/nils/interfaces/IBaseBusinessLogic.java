package com.nils.interfaces;

import java.io.Serializable;
import java.util.List;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public interface IBaseBusinessLogic<T,ID extends Serializable> {

    boolean exists(List<String> ids);

    T findById(ID id);

    List<T> findByIds(List<ID> ids);

    void delete(ID id);

    void delete(List<ID> ids);

    void save(T entity);

    void save(List<T> entities);

    void update(T entity);

    void update(List<T> entities);
}
