package com.transport.logic.transport;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;
import akka.pattern.Patterns;
import akka.remote.RemotingLifecycleEvent;
import akka.util.Timeout;
import com.nils.entities.transport.Error;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import com.transport.actor.AssotiationActor;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.Await;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static akka.dispatch.Futures.sequence;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class FEAkkaTransport<T, ID extends Serializable> implements ITransportLayer<T, ID> {

    private Logger logger = LoggerFactory.getLogger(FEAkkaTransport.class);
    protected ActorSystem system;
    protected ActorRef beMasterActor;
    private Duration timeoutDuration = Duration.create("100 seconds");
    private long secondsTimeout = 100;

    public FEAkkaTransport() throws Exception {
        logger.info("Starting FEActorSystem");
        system = ActorSystem.create("feactorsystem", ConfigFactory.load().getConfig("feconfig"));
        String remotePath = "akka.tcp://beactorsystem@127.0.0.1:2554/user/BEMasterActor";
        ActorSelection actorSelection = system.actorSelection(remotePath);
        ActorRef assotiationActor = system.actorOf(Props.create(AssotiationActor.class));
        Future<ActorRef> actorLocalRefFuture = actorSelection.resolveOne(new Timeout(Duration.create(secondsTimeout, TimeUnit.SECONDS)));
        try {
            logger.info("Acquiring BEMasterActor handle...");
            beMasterActor = Await.result(actorLocalRefFuture, Duration.create(secondsTimeout, TimeUnit.SECONDS));
            logger.info("BEMasterActor handle was acquired");
        } catch (Exception e) {
            logger.info("Failed to acquire BEMasterActor handle!!!", e);
            throw new Exception("Failed to acquire BEMasterActor handle");
        }
        system.eventStream().subscribe(assotiationActor,RemotingLifecycleEvent.class);

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
    public void findByIdsWithActor(String entityType, List<ID> ids, ICallBack callBack) {
        executeRequest(new Request(null, entityType, Request.Action.GET, (Serializable) ids), callBack);
    }

    @Override
    public void findByProperties(String entityType, Map<String, ID> properties, ICallBack callBack) {
        executeRequest(new Request(null, entityType, Request.Action.FIND_BY_PROPERTY,(Serializable)properties), callBack);
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

    @Override
    public void orchestrate(List<Request> requests, final List<Response> responses) {

        logger.info("Going to execute orchestration, #requests: {}", requests.size());

        final ExecutionContext ec = system.dispatcher();
        List<Future<Object>> futures = new LinkedList<>();
        for (Request request : requests) {
            futures.add(Patterns.ask(beMasterActor, request, new Timeout(Duration.create(secondsTimeout, TimeUnit.SECONDS))));
        }
        Future<Iterable<Object>> futureSequence = sequence(futures, ec);
        try {
            Iterable<Object> results = Await.result(futureSequence, timeoutDuration);
            for (Object result : results) {
                if (result instanceof Response) {
                    responses.add((Response) result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void executeRequest(final Request request, final ICallBack callBack) {
        executeRequest_blocking(request, callBack);
    }

    protected void executeRequest_nonBlocking(final Request request, final ICallBack callBack) {
        final ExecutionContext ec = system.dispatcher();
        Future<Object> future1 = Patterns.ask(beMasterActor, request, new Timeout(Duration.create(secondsTimeout, TimeUnit.SECONDS)));
        List<Future<Object>> futures = new LinkedList<>();
        futures.add(future1);
        Future<Iterable<Object>> futureSequence = sequence(futures, ec);
        futureSequence.onSuccess(new OnSuccess<Iterable<Object>>() {
            @Override
            public void onSuccess(Iterable<Object> objects) throws Throwable {
                for (Object object : objects) {
                    if (object instanceof Response) {
                        Response response = (Response) object;
                        callBack.onResponse(response);
                    }
                }
            }
        }, ec);
    }

    protected void executeRequest_blocking(final Request request, final ICallBack callBack) {
        Future<Object> future = Patterns.ask(beMasterActor, request, new Timeout(Duration.create(secondsTimeout, TimeUnit.SECONDS)));
        Timeout timeout = new Timeout(Duration.create(secondsTimeout, "seconds"));
        try {
            Object result = Await.result(future, timeout.duration());
            if (callBack != null) {
                if (result instanceof Response) {
                    callBack.onResponse((Response) result);
                }
                if (result instanceof Error) {
                    callBack.onError((Error) result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeRequestNonBlocking(final Request request, final ICallBack callBack) {
        Future<Object> future = Patterns.ask(beMasterActor, request, new Timeout(Duration.create(secondsTimeout, TimeUnit.SECONDS)));
        final ExecutionContext ec = system.dispatcher();
        future.onSuccess(new OnSuccess<Object>() {
            public void onSuccess(Object result) {
                if (result instanceof Response) {
                    logger.info("Successfully call {} action. ", request.getAction());
                    callBack.onResponse((Response) result);
                }
                if (result instanceof Error) {
                    logger.info("Successfully call {} action. ", request.getAction());
                    callBack.onError((Error) result);
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
