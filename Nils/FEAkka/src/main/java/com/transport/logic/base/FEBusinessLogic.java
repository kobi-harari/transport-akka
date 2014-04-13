package com.transport.logic.base;

import com.google.inject.Inject;
import com.nils.entities.transport.Request;
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
    Class clazz;
    ITransportLayer transportLayer;

    public FEBusinessLogic(ITransportLayer transportLayer) {
        this.transportLayer = transportLayer;
    }

    public void setClazz(Class clazz) {
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
                logger.error("Failed to check existence! errorCode: {}, errorMessage: {}", error.getErrorCode(), error.getMessage());
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
                logger.error("Failed to find by id! errorCode: {}, errorMessage: {}", error.getErrorCode(), error.getMessage());
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
                logger.error("Delete entities completed!");
            }

            @Override
            public void onError(Error error) {
                logger.error("Failed to Delete by id! errorCode: {}, errorMessage: {}", error.getErrorCode(), error.getMessage());
            }
        });
    }

    @Override
    public void save(List<T> entities) {
        logger.debug("save {} in bulk, bulkSize: {}", clazz.getSimpleName(), entities.size());
        transportLayer.saveEntities(clazz.getSimpleName(), entities, new ICallBack() {
            @Override
            public void onResponse(Response response) {
                logger.error("Save entities completed!");
            }

            @Override
            public void onError(Error error) {
                logger.error("Failed to save entities! errorCode: {}, errorMessage: {}", error.getErrorCode(), error.getMessage());
            }
        });
    }

    @Override
    public void update(List<T> entities) {
        logger.debug("update {} in bulk, bulkSize: {}", clazz.getSimpleName(), entities.size());
        transportLayer.updateEntities(clazz.getSimpleName(), entities, new ICallBack() {
            @Override
            public void onResponse(Response response) {
                logger.error("Update entities completed!");
            }

            @Override
            public void onError(Error error) {
                logger.error("Failed to update entities! errorCode: {}, errorMessage: {}", error.getErrorCode(), error.getMessage());
            }
        });
    }

    protected List<Response> orchestrate(List<Request> requests) {
        final List<Response> responses = new LinkedList<>();
        transportLayer.orchestrate(requests, responses);
        return responses;
    }
}
