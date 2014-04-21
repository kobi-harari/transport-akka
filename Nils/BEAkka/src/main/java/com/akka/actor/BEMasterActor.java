package com.akka.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.akka.actor.logic.AccountActor;
import com.akka.actor.logic.OrderActor;
import com.akka.actor.logic.OrderItemActor;
import com.akka.actor.logic.UserActor;
import com.akka.system.IocInitializer;
import com.akka.system.SystemModule;
import com.google.inject.Module;
import com.nils.entities.transport.Error;
import com.nils.entities.transport.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class BEMasterActor extends UntypedActor {

    private static final Logger logger = LoggerFactory.getLogger(BEMasterActor.class);

    private final ActorRef userActor;
    private final ActorRef accountActor;
    private final ActorRef orderActor;
    private final ActorRef orderItemActor;


    public BEMasterActor() {
        logger.info("creating the master dispatcher for this be service");

        List<Module> moduleList = new ArrayList<Module>(1);
        moduleList.add(new SystemModule());
        IocInitializer.getInstance().setModules(moduleList);


        userActor = getContext().actorOf(
                Props.create(UserActor.class, IocInitializer.getInstance().getInjector()), "userActor");
        accountActor = getContext().actorOf(
                Props.create(AccountActor.class, IocInitializer.getInstance().getInjector()), "accountActor");
        orderActor = getContext().actorOf(
                Props.create(OrderActor.class, IocInitializer.getInstance().getInjector()), "orderActor");
        orderItemActor = getContext().actorOf(
                Props.create(OrderItemActor.class, IocInitializer.getInstance().getInjector()), "orderItemActor");
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
                    case "OrderItem":
                        logger.info("BEMasterActor forwarding msg to AccountActor, {}", message);
                        accountActor.forward(message, getContext());
                        break;
                    case "Order":
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

//    /**
//     * Strategy for Master Actor children
//     */
//    private static SupervisorStrategy strategy =
//            new OneForOneStrategy(10, Duration.create("1 minute"),
//                    new Function<Throwable, SupervisorStrategy.Directive>() {
//                        @Override
//                        public SupervisorStrategy.Directive apply(Throwable t) {
//                            if (t instanceof MessagingException) {
//                                return resume();
//                            } else if (t instanceof Exception) {
//                                return stop();
//                            } else {
//                                return escalate();
//                            }
//                        }
//                    });
//
//    @Override
//    public SupervisorStrategy supervisorStrategy() {
//        return strategy;
//    }

}
