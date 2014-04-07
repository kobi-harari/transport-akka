package com.transport.logic.transport;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.OnComplete;
import akka.dispatch.OnSuccess;
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
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class FEAkkaTransport<T, ID extends Serializable> implements ITransportLayer<T, ID> {
    Logger logger = LoggerFactory.getLogger(FEAkkaTransport.class);
    ActorSystem system;
    ActorRef feMasterActor;
    ActorRef beMasterActor;
    ActorRef mockMasterActor;

    public FEAkkaTransport() {
        logger.info("Starting FEActorSystem");
        system = ActorSystem.create("feactorsystem", ConfigFactory.load().getConfig("feconfig"));
        feMasterActor = system.actorOf(Props.create(FEMasterActor.class), "FEMasterActor");

//        Timeout timeout1 = new Timeout(Duration.create(5, "seconds"));
//        Future<Object> future = Patterns.ask(feMasterActor, "hi", timeout1);
//
//        // this blocks current running thread
//        try {
//            Awaitable<Object> ready = Await.ready(future, timeout1.duration());
//
//            String result = (String) Await.result(future, timeout1.duration());
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String remotePath = "akka.tcp://beactorsystem@127.0.0.1:2554/user/BEMasterActor";
        ActorSelection actorSelection1 = system.actorSelection(remotePath);
        Timeout timeout = new Timeout(Duration.create(30, TimeUnit.SECONDS));
        Future<ActorRef> actorLocalRefFuture = actorSelection1.resolveOne(timeout);
        try {
            beMasterActor = Await.result(actorLocalRefFuture, Duration.create(5, TimeUnit.SECONDS));
            Request request = new Request(null, "User", Request.Action.GET, (Serializable) Arrays.asList("1"));
            Future<Object> futureGet = Patterns.ask(beMasterActor, request, timeout);
            Response response = (Response) Await.result(futureGet, timeout.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ActorSelection actorSelection = system.actorSelection("akka.tcp://beactorsystem@127.0.0.1:2554/user/MockMasterActor");
        Future<ActorRef> mockActorRefFuture = actorSelection.resolveOne(new Timeout(Duration.create(30, TimeUnit.SECONDS)));
        try {
            mockMasterActor = Await.result(mockActorRefFuture, Duration.create(5, TimeUnit.SECONDS));
        } catch (Exception e) {
            e.printStackTrace();
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
        Future<Object> future = Patterns.ask(mockMasterActor, request, new Timeout(Duration.create(15, TimeUnit.SECONDS)));

        try {
            new OnSuccess<Object>() {
                @Override
                public void onSuccess(Object o) throws Throwable {
                    System.out.println("bla");
                }
            }.onSuccess(future);
        } catch (Throwable t) {

        }
        final ExecutionContext ec = system.dispatcher();

        future.onSuccess(new OnSuccess<Object>() {
            public void onSuccess(Object result) {
//                if ("bar" == result) {
//                    //Do something if it resulted in "bar"
//                } else {
//                    //Do something if it was some other String
//                }
//                callBack.onResponse(result);

                if(result instanceof Response){
                    System.out.println("aaa");
                    callBack.onResponse((Response)result);
                }
            }
        }, ec);

//        try {
//            Object result = Await.result(future, Duration.create(15, TimeUnit.SECONDS));
//            if (result instanceof Response) {
//                callBack.onResponse((Response) result);
//            } else {
//                if (result instanceof Error) {
//                    callBack.onError((Error) result);
//                } else {
//                    logger.error("Result is not a Response nor Error, result: {}", result);
//                }
//            }
//        } catch (Exception e) {
//            logger.error("Error in receiving Message", e);
//        }
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
