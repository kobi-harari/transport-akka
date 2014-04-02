package com.nils.feimpl;

import com.nils.entities.transport.Response;
import com.nils.interfaces.IBaseBusinessLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class BaseBusinessLogic<T, ID extends Serializable> implements IBaseBusinessLogic<T, ID> {

    private static final Logger logger = LoggerFactory.getLogger(BaseBusinessLogic.class);

    ITransportLayer transportLayer; //TODO inject
    Class clazz;

    public BaseBusinessLogic(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public T findById(ID id) {
        logger.debug("find {} by Id {}", clazz.getSimpleName(), id);
        final List<T> toReturn = new LinkedList<>();
        transportLayer.findByIds(clazz.getSimpleName(), Arrays.asList(id), new ICallBack() {
            @Override
            public void onResponse(Response response) {
                toReturn.addAll((List<T>) response.getMessage());
            }

            @Override
            public void onError(Error error) {
                //TODO
            }
        });
        return toReturn.get(0);
    }

    @Override
    public List<T> findByIds(List<ID> ids) {
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
    public void delete(ID id) {
        this.delete(Arrays.asList(id));
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
    public void save(T entity) {
        save(Arrays.asList(entity));
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
    public void update(T entity) {
        update(Arrays.asList(entity));
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
