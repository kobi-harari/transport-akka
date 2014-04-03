package com.akka.logic.actors;

import akka.actor.UntypedActor;
import com.nils.entities.transport.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class BEMasterActor extends UntypedActor {

    private static final Logger logger = LoggerFactory.getLogger(BEMasterActor.class);

    @Override
    public void onReceive(Object message) throws Exception {
        logger.info("BEMasterActor got message! {}", message);
        if (message instanceof String) {
            if (message.equals("hi")) {
                getSender().tell("hi there!", getSelf());
            }
            if(message.equals("bye")) {
                getSender().tell("BYE!!!", getSelf());
            }
            getSender().tell("I don't understand you!!!", getSelf());
        } else {
            logger.error("unhandled message");
            unhandled(message);
        }

    }
}
