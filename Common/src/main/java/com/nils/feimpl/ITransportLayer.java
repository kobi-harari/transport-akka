package com.nils.feimpl;

import java.io.Serializable;
import java.util.List;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public interface ITransportLayer<T, ID extends Serializable> {

    void findByIds(String entityType, List<ID> ids, ICallBack callBack);

    void deleteEntities(String entityType, List<T> ids, ICallBack callBack);

    void saveEntities(String entityType, List<T> entities, ICallBack callBack);

    void updateEntities(String entityType, List<T> entities, ICallBack callBack);
}
