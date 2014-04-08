package com.transport.logic.transport;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import com.transport.logic.transport.beactors.FEMasterActor;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.Await;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class FEAkkaTransport<T, ID extends Serializable> implements ITransportLayer<T, ID> {
    Logger logger = LoggerFactory.getLogger(FEAkkaTransport.class);
    ActorSystem system;
    ActorRef beMasterActor;

    public FEAkkaTransport() {
        logger.info("Starting FEActorSystem");
        system = ActorSystem.create("feactorsystem", ConfigFactory.load().getConfig("feconfig"));

        String remotePath = "akka.tcp://beactorsystem@127.0.0.1:2554/user/BEMasterActor";
        ActorSelection actorSelection = system.actorSelection(remotePath);
        Future<ActorRef> actorLocalRefFuture = actorSelection.resolveOne(new Timeout(Duration.create(30, TimeUnit.SECONDS)));
        try {
            beMasterActor = Await.result(actorLocalRefFuture, Duration.create(15, TimeUnit.SECONDS));
            logger.info("BEMaster Actor handle was acquired!");
        } catch (Exception e) {
            logger.info("Failed to acquire BEMaster Actor!!!", e);
        }
    }

    @Override
    public void exists(String entityType, List<ID> ids, ICallBack callBack) {
        //TODO
    }

    @Override
    public void findByIds(String entityType, List<ID> ids, final ICallBack callBack) {
        logger.info("FETransport - findByIds");
        StringBuilder sb = new StringBuilder();
        for (Serializable id : ids) {
            sb.append(id).append("\n");
        }
        logger.info("ids: {}", sb.toString());

        Request request = new Request(null, entityType, Request.Action.GET, (Serializable) ids);
        Future<Object> future = Patterns.ask(beMasterActor, request, new Timeout(Duration.create(15, TimeUnit.SECONDS)));
        final ExecutionContext ec = system.dispatcher();
        future.onSuccess(new OnSuccess<Object>() {
            public void onSuccess(Object result) {
                if (result instanceof Response) {
                    logger.info("got findByIds result from BE Actor");
                    callBack.onResponse((Response) result);
                }
            }
        }, ec);

        future.onFailure(new OnFailure() {
            @Override
            public void onFailure(Throwable throwable) throws Throwable {
                logger.error("Failed to get response", throwable);
            }
        }, ec);

        try {
            Thread.sleep(1000 * 15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
