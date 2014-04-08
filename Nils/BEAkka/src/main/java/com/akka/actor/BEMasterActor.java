package com.akka.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.akka.actor.logic.AccountActor;
import com.akka.actor.logic.UserActor;
import com.akka.system.IocInitializer;
import com.nils.entities.transport.Error;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class BEMasterActor extends UntypedActor {

    private static final Logger logger = LoggerFactory.getLogger(BEMasterActor.class);
    private final ActorRef userActor;
    private final ActorRef accountActor;

    public BEMasterActor() {
        logger.info("creating the master dispatcher for this be service");
        userActor = getContext().actorOf(Props.create(UserActor.class, IocInitializer.getInstance().getInjector()), "userActor");
        accountActor = getContext().actorOf(Props.create(AccountActor.class, IocInitializer.getInstance().getInjector()), "accountActor");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        logger.info("BEMasterActor got message! {}", message);
        if (message instanceof Request) {
            if (((Request) message).getAction() != null) {
                switch (((Request) message).getService()) {
                    case "User":
                        userActor.forward(message, getContext());
                        break;
                    case "Account":
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
}
