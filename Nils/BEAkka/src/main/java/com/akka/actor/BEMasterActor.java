package com.akka.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.akka.actor.logic.UserActor;
import com.akka.system.IocInitializer;
import com.nils.entities.transport.Error;
import com.nils.entities.transport.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class BEMasterActor extends UntypedActor {

    private static final Logger logger = LoggerFactory.getLogger(BEMasterActor.class);
    private final ActorRef userActor;

    public BEMasterActor() {
        logger.info("creating the master dispatcher for this be service");
        userActor = getContext().actorOf(Props.create(UserActor.class, IocInitializer.getInstance().getInjector()), "userActor");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        logger.info("BEMasterActor got message! {}", message);
        if (message instanceof Request) {
            if (((Request) message).getAction() != null) {
                userActor.tell(message, getSelf());
            } else {
                logger.error("what is the action? we do not know what to do. Notify my sender about it.");
                userActor.tell(new Error(123, "there is no action I don't know what to do", null), getSender());
            }
        }
    }
}
