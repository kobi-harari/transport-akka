package com.transport.logic.base;

import com.google.inject.Inject;
import com.nils.entities.transport.Response;
import com.nils.entities.transport.Error;
import com.nils.interfaces.IBaseBusinessLogic;
import com.transport.logic.transport.FEAkkaTransport;
import com.transport.logic.transport.ICallBack;
import com.transport.logic.transport.ITransportLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class FEBusinessLogic<T, ID extends Serializable> implements IBaseBusinessLogic<T, ID> {

    private static final Logger logger = LoggerFactory.getLogger(FEBusinessLogic.class);

    @Inject
    ITransportLayer transportLayer;
    Class clazz;

    public FEBusinessLogic(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean exists(Serializable id) {
        return false;
    }

    @Override
    public boolean exists(List<ID> ids) {
        logger.debug("check exists for {} by bulks ids, idList size: {}", clazz.getSimpleName(), ids.size());
        final Boolean[] toReturn = {new Boolean(false)};
        transportLayer.exists(clazz.getSimpleName(), ids, new ICallBack() {
            @Override
            public void onResponse(Response response) {
                toReturn[0] = (Boolean) response.getMessage();
            }

            @Override
            public void onError(Error error) {
                //TODO
            }
        });
        return toReturn[0];
    }


    @Override
    public List<T> find(List<ID> ids) {
        logger.debug("find {} by ids, idList size: {}", clazz.getSimpleName(), ids.size());
        final List<T> toReturn = new LinkedList<>();
        transportLayer.findByIds(clazz.getSimpleName(), ids, new ICallBack() {
            @Override
            public void onResponse(Response response) {
                toReturn.addAll((List<T>) response.getMessage());
            }

            @Override
            public void onError(Error error) {
                //TODO
            }
        });
        return toReturn;
    }

    @Override
    public void delete(List<ID> ids) {
        logger.debug("delete {} in bulk, bulkSize: {}", clazz.getSimpleName(), ids.size());
        transportLayer.deleteEntities(clazz.getSimpleName(), ids, new ICallBack() {
            @Override
            public void onResponse(Response response) {
                //TODO
            }

            @Override
            public void onError(Error error) {
                //TODO
            }
        });
    }

    @Override
    public void save(List<T> entities) {
        logger.debug("save {} in bulk, bulkSize: {}", clazz.getSimpleName(), entities.size());
        transportLayer.saveEntities(clazz.getSimpleName(), entities, new ICallBack() {
            @Override
            public void onResponse(Response response) {
                //TODO
            }

            @Override
            public void onError(Error error) {
                //TODO
            }
        });
    }

    @Override
    public void update(List<T> entities) {
        logger.debug("update {} in bulk, bulkSize: {}", clazz.getSimpleName(), entities.size());
        transportLayer.updateEntities(clazz.getSimpleName(), entities, new ICallBack() {
            @Override
            public void onResponse(Response response) {
                //TODO
            }

            @Override
            public void onError(Error error) {
                //TODO
            }
        });
    }
}
