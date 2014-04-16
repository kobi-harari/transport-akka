package com.transport.logic.transport;

import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public interface ITransportLayer<T, ID extends Serializable> {

    void exists(String entityType, List<ID> ids, ICallBack callBack);

    void findByIds(String entityType, List<ID> ids, ICallBack callBack);

    void findByProperties(String entityType, Map<String,ID> properties, ICallBack callBack);

    void deleteEntities(String entityType, List<T> ids, ICallBack callBack);

    void saveEntities(String entityType, List<T> entities, ICallBack callBack);

    void updateEntities(String entityType, List<T> entities, ICallBack callBack);

    void orchestrate(List<Request> requests, final List<Response> responses);
}
