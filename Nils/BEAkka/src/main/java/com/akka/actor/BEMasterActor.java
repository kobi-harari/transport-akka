package com.akka.actor;

import akka.actor.*;
import akka.japi.Function;
import akka.remote.routing.RemoteRouterConfig;
import akka.routing.DefaultResizer;
import akka.routing.Resizer;
import akka.routing.RoundRobinRouter;
import akka.routing.RouterConfig;
import com.akka.actor.logic.AccountActor;
import com.akka.actor.logic.UserActor;
import com.akka.system.IocInitializer;
import com.nils.entities.transport.Error;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.escalate;
import static akka.actor.SupervisorStrategy.resume;
import static akka.actor.SupervisorStrategy.stop;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class BEMasterActor extends UntypedActor {

    private static final Logger logger = LoggerFactory.getLogger(BEMasterActor.class);

    private final ActorRef userActor;
    private final ActorRef accountActor;


    public BEMasterActor() {
        logger.info("creating the master dispatcher for this be service");

//        Resizer resizer = new DefaultResizer(1,10);
        Config beconfig = ConfigFactory.load().getConfig("beconfig");
//        RouterConfig routerConfig = new RemoteRouterConfig();
//
//        Deploy deploy = new Deploy(RouterConfig );

        userActor = getContext().actorOf(
                Props.create(UserActor.class, IocInitializer.getInstance().getInjector()).withRouter(new RoundRobinRouter(5)), "userActor");
        accountActor = getContext().actorOf(
                Props.create(AccountActor.class, IocInitializer.getInstance().getInjector()), "accountActor");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        logger.info("BEMasterActor got message! {}", message);
        context().system().eventStream().publish(message);
        if (message instanceof Request) {
            if (((Request) message).getAction() != null) {
                switch (((Request) message).getService()) {
                    case "User":
                        logger.info("BEMasterActor forwarding msg to UserActor, {}", message);
                        userActor.forward(message, getContext());
                        break;
                    case "Account":
                        logger.info("BEMasterActor forwarding msg to AccountActor, {}", message);
                        accountActor.forward(message, getContext());
                        break;
                    default:
                        logger.error("This service {} is not available", ((Request) message).getService());
                        getSender().tell(new Error(Error.SERVICE_NOT_AVAILABLE,
                                ((Request) message).getService() + " is not available", null),
                                getSelf());
                }
            } else {
                logger.error("what is the action? we do not know what to do. Notify my sender about it.");
                getSender().tell(new Error(Error.ACTION_MISSING, "missing action!", null), getSelf());
            }
        }
    }

    /**
     * Strategy for Master Actor children
     */
    private static SupervisorStrategy strategy =
            new OneForOneStrategy(10, Duration.create("1 minute"),
                    new Function<Throwable, SupervisorStrategy.Directive>() {
                        @Override
                        public SupervisorStrategy.Directive apply(Throwable t) {
                            if (t instanceof MessagingException) {
                                return resume();
                            } else if (t instanceof Exception) {
                                return stop();
                            } else {
                                return escalate();
                            }
                        }
                    });

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

}
