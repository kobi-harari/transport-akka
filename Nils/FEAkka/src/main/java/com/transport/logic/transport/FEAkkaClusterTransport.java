package com.transport.logic.transport;

import akka.actor.*;
import akka.cluster.Cluster;
import akka.cluster.routing.AdaptiveLoadBalancingPool;
import akka.cluster.routing.ClusterRouterPool;
import akka.cluster.routing.ClusterRouterPoolSettings;
import akka.cluster.routing.SystemLoadAverageMetricsSelector;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.akka.actor.BEMasterActor;
import com.nils.entities.transport.Error;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by kobi on 4/20/14.
 */
public class FEAkkaClusterTransport<T, ID extends Serializable> implements ITransportLayer<T, ID> {
    private Logger logger = LoggerFactory.getLogger(FEAkkaTransport.class);

    protected ActorSystem system;
    private ActorRef backend;


    public FEAkkaClusterTransport() {
        logger.info("Starting a new backend cluster");

        final Config config =  ConfigFactory.parseString(
                "akka.cluster.roles = [frontend]").withFallback(
                ConfigFactory.load("application_cluster.conf"));
        system = ActorSystem.create("ClusterSystem", config);
        initClusterBackend();
    }

    @Override
    public void exists(String entityType, List<ID> ids, ICallBack callBack) {

    }

    @Override
    public void findByIds(String entityType, List<ID> ids, ICallBack callBack) {
        logger.debug("get entity by ids {}" , ids);
        executeRequest(new Request(null, entityType, Request.Action.GET, (Serializable) ids), callBack);
    }

    @Override
    public void findByIdsWithActor(String entityType, List<ID> ids, ICallBack callBack) {

    }

    @Override
    public void findByProperties(String entityType, Map<String, ID> properties, ICallBack callBack) {

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

    @Override
    public void orchestrate(List<Request> requests, List<Response> responses) {

    }

    protected void executeRequest(final Request request, final ICallBack callBack) {
        executeRequest_blocking(request, callBack);
    }

    protected void executeRequest_blocking(final Request request, final ICallBack callBack) {
        long secondsTimeout = 100;
        Future<Object> future = Patterns.ask(backend, request, new Timeout(Duration.create(secondsTimeout, TimeUnit.SECONDS)));
        Timeout timeout = new Timeout(Duration.create(secondsTimeout, "seconds"));
        try {
            Object result = Await.result(future, timeout.duration());
            if (callBack != null) {
                if (result instanceof Response) {
                    callBack.onResponse((Response) result);
                }
                if (result instanceof com.nils.entities.transport.Error) {
                    callBack.onError((Error) result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initClusterBackend() {
        //#router-deploy-in-code
        int totalInstances = 10;
        int maxInstancesPerNode = 2;
        boolean allowLocalRoutees = true;
        String useRole = "backend";
        backend = system.actorOf(
                new ClusterRouterPool(new AdaptiveLoadBalancingPool(
                        SystemLoadAverageMetricsSelector.getInstance(), 0),
                        new ClusterRouterPoolSettings(totalInstances, maxInstancesPerNode,
                                allowLocalRoutees, useRole)
                ).props(Props.create(BEMasterActor.class)), "BEMasterActor"
        );
    }


}
