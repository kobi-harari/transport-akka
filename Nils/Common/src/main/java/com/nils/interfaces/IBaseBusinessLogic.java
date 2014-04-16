package com.nils.interfaces;

import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public interface IBaseBusinessLogic<T,ID extends Serializable> {

    boolean exists(Serializable id);

    boolean exists(List<ID> ids);

    List<T> find(List<ID> ids);

    List<T> findByProperties(Map<String,Object> propertise);

    void delete(List<ID> ids);

    void save(List<T> entities);

    void update(List<T> entities);

}
