package com.transport.logic.transport;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import com.nils.entities.transport.Error;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.Await;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class FEAkkaTransport<T, ID extends Serializable> implements ITransportLayer<T, ID> {

    private Logger logger = LoggerFactory.getLogger(FEAkkaTransport.class);
    private ActorSystem system;
    private ActorRef beMasterActor;
    private long secondsTimeout = 10;

    public FEAkkaTransport() {
        logger.info("Starting FEActorSystem");
        system = ActorSystem.create("feactorsystem", ConfigFactory.load().getConfig("feconfig"));
        String remotePath = "akka.tcp://beactorsystem@127.0.0.1:2554/user/BEMasterActor";
        ActorSelection actorSelection = system.actorSelection(remotePath);
        Future<ActorRef> actorLocalRefFuture = actorSelection.resolveOne(new Timeout(Duration.create(secondsTimeout, TimeUnit.SECONDS)));
        try {
            logger.info("Acquiring BEMasterActor handle...");
            beMasterActor = Await.result(actorLocalRefFuture, Duration.create(secondsTimeout, TimeUnit.SECONDS));
            logger.info("BEMasterActor handle was acquired");
        } catch (Exception e) {
            logger.info("Failed to acquire BEMasterActor handle!!!", e);
        }
    }

    @Override
    public void exists(String entityType, List<ID> ids, final ICallBack callBack) {
//        executeRequest(new Request(null, entityType, Request.Action., (Serializable) ids), callBack);
    }

    @Override
    public void findByIds(String entityType, List<ID> ids, final ICallBack callBack) {
        executeRequest(new Request(null, entityType, Request.Action.GET, (Serializable) ids), callBack);
    }

    @Override
    public void deleteEntities(String entityType, List<T> ids, final ICallBack callBack) {
        executeRequest(new Request(null, entityType, Request.Action.DELETE, (Serializable) ids), callBack);
    }

    @Override
    public void saveEntities(String entityType, List<T> entities, final ICallBack callBack) {
        executeRequest(new Request(null, entityType, Request.Action.SAVE, (Serializable) entities), callBack);
    }

    @Override
    public void updateEntities(String entityType, List<T> entities, final ICallBack callBack) {
        executeRequest(new Request(null, entityType, Request.Action.UPDATE, (Serializable) entities), callBack);
    }

    private void executeRequest(final Request request, final ICallBack callBack) {
        Future<Object> future = Patterns.ask(beMasterActor, request, new Timeout(Duration.create(secondsTimeout, TimeUnit.SECONDS)));
        final ExecutionContext ec = system.dispatcher();
        future.onSuccess(new OnSuccess<Object>() {
            public void onSuccess(Object result) {
                if (result instanceof Response) {
                    logger.info("Successfully call {} action. ", request.getAction());
                    callBack.onResponse((Response) result);
                }
            }
        }, ec);

        future.onFailure(new OnFailure() {
            @Override
            public void onFailure(Throwable throwable) throws Throwable {
                logger.error("Failed to call {} action. ", request.getAction());
                String errorMessage = "Transport Error in Action: " + request.getAction();
                Error error = new Error(Error.TRANSPORT_ERROR, errorMessage, throwable);
                callBack.onError(error);
            }
        }, ec);
    }
}
