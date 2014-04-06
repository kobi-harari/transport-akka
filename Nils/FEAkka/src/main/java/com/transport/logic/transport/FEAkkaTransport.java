package com.transport.logic.transport;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.nils.entities.transport.*;
import com.nils.entities.transport.Error;
import com.transport.logic.transport.beactors.FEMasterActor;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.Await;
import scala.concurrent.Awaitable;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

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
    ActorRef feMasterActor;
    ActorRef beMasterActor;

    public FEAkkaTransport() {
        logger.info("Starting FEActorSystem");
        system = ActorSystem.create("feactorsystem", ConfigFactory.load().getConfig("feconfig"));
        feMasterActor = system.actorOf(Props.create(FEMasterActor.class), "FEMasterActor");

        Timeout timeout1 = new Timeout(Duration.create(5, "seconds"));
        Future<Object> future = Patterns.ask(feMasterActor, "hi", timeout1);

        // this blocks current running thread
        try {
            Awaitable<Object> ready = Await.ready(future, timeout1.duration());

            String result = (String) Await.result(future, timeout1.duration());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        ActorSelection actorSelection = system.actorSelection("akka://beactorsystem/user/BEMasterActor");
        ActorRef actorRef = system.actorFor("akka://beactorsystem@127.0.0.1:2554/user/BEMasterActor");
        Request refResuest = new Request(null,"User", Request.Action.GET, (Serializable)Arrays.asList("1"));
        Future<Object> futureRef = Patterns.ask(actorRef, refResuest, timeout1);
        try {
            Response response = (Response)Await.result(futureRef, timeout1.duration());
            System.out.println("aaa");
        } catch (Exception e) {
            e.printStackTrace();
        }


        ActorSelection actorSelection = system.actorSelection("akka://beactorsystem@localhost:2554/user/BEMasterActor");
        Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
        Future<ActorRef> actorRefFuture = actorSelection.resolveOne(timeout);
        boolean res = actorRefFuture.isCompleted();
        try {
            beMasterActor = Await.result(actorRefFuture, Duration.create(5, TimeUnit.SECONDS));
            Request request = new Request(null,"User", Request.Action.GET, (Serializable)Arrays.asList("1"));
            Future<Object> futureGet = Patterns.ask(beMasterActor, request, timeout1);
            Response response = (Response)Await.result(futureGet, timeout1.duration());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exists(String entityType, List<ID> ids, ICallBack callBack) {
        //TODO
    }

    @Override
    public void findByIds(String entityType, List<ID> ids, ICallBack callBack) {
        logger.info("FETransport - findByIds");
        StringBuilder sb = new StringBuilder();
        for (Serializable id : ids) {
            sb.append(id).append("\n");
        }
        logger.info("ids: {}", sb.toString());

        Request request = new Request(null, entityType, Request.Action.GET, (Serializable) ids);
        Timeout timeout = new Timeout(Duration.create(15, TimeUnit.SECONDS));
        Future<Object> future = Patterns.ask(feMasterActor, request, timeout);
        try {
            Object result = Await.result(future, timeout.duration());
            if (result instanceof Response) {
                callBack.onResponse((Response) result);
            } else {
                if (result instanceof Error) {
                    callBack.onError((Error) result);
                } else {
                    logger.error("Result is not a Response nor Error, result: {}", result);
                }
            }
        } catch (Exception e) {
            logger.error("Error in receiving Message", e);
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
