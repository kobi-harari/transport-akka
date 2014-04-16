package com.akka.interfaces;

import com.nils.entities.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by kobi on 4/5/14.
 */
public interface DbOperations <T extends BaseEntity,ID extends Serializable>{

    boolean exists(ID id);

    <T> List<T> find(List<ID> ids);

    <T> List<T> findByProperties(Map<String, Object> properties);

    <T> T find(ID id);

    void delete(List<ID> ids);

    void delete(ID id);

    void save(List<T> entities);

    void save(T entity);

    void update(List<T> entities);

    void update(T entity);

}
