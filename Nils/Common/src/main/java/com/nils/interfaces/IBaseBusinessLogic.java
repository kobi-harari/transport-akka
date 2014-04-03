package com.nils.interfaces;

import java.io.Serializable;
import java.util.List;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public interface IBaseBusinessLogic<T,ID extends Serializable> {

    boolean exists(List<String> ids);

    List<T> find(List<ID> ids);

    void delete(List<ID> ids);

    void save(List<T> entities);

    void update(List<T> entities);
}
