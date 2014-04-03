package com.transport.logic.transport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class AkkaTransport<T, ID extends Serializable> implements ITransportLayer<T, ID>{

    public AkkaTransport() {
        //bring up ActorSystem
    }

    @Override
    public void exists(String entityType, List<ID> ids, ICallBack callBack) {
        //TODO
    }

    @Override
    public void findByIds(String entityType, List<ID> ids, ICallBack callBack) {

    }

    @Override
    public void deleteEntities(String entityType, List<T> ids, ICallBack callBack) {

    }

    @Override
    public void saveEntities(String entityType, List<T> entities, ICallBack callBack) {

    }

    @Override
    public void updateEntities(String entityType, List<T> entities, ICallBack callBack) {

    }
}
