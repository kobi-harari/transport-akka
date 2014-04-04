package com.akka.interfaces;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kobi on 4/5/14.
 */
public interface DbOperations <T,ID extends Serializable>{

    boolean exists(ID id);

    List<T> find(List<ID> ids);

    T find(ID id);

    void delete(List<ID> ids);

    void delete(ID id);

    void save(List<T> entities);

    void save(T entityy);

    void update(List<T> entities);

    void update(T entity);

}
