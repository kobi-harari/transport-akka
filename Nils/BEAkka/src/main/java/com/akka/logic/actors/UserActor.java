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
            logger.debug("received message!", request);
            switch (request.getAction()){
                case GET:
                    logger.debug("save");
//                    userLogic.sa
                    break;
                case DELETE:
                    logger.debug("delete");
                    break;
                case SAVE:
                    logger.debug("save");
                    break;
                case UPDATE:
                    logger.debug("update");
                    break;
                default:
                    logger.error("non valid action");
            }
            getSender().tell("done!",getSelf());
        } else{
            logger.error("unhandled message");
            unhandled(message);
        }
    }

}