package com.akka.logic.actors;

import akka.actor.UntypedActor;
import com.akka.interfaces.IBEUserBusinessLogic;
import com.nils.entities.transport.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class UserActor extends UntypedActor {

    IBEUserBusinessLogic userLogic;

    private static final Logger logger = LoggerFactory.getLogger(UserActor.class);

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof Request) {
            Request request = (Request) message;
            switch (request.getAction()){
                case "save":
                    logger.debug("save");
                    break;
                case "delete":
                    logger.debug("delete");
                    break;
                default:
                    logger.error("non valid action");
            }
            logger.debug("received message!", request);
            getSender().tell("done!",getSelf());
        } else{
            logger.error("unhandled message");
            unhandled(message);
        }
    }

}