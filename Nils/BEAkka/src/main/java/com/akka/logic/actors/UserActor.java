package com.akka.logic.actors;

import akka.actor.UntypedActor;
import com.akka.interfaces.IBEUserBusinessLogic;
import com.google.inject.Inject;
import com.nils.entities.User;
import com.nils.entities.transport.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created by uri.silberstein on 4/3/14.
 */
public class UserActor extends UntypedActor {

    @Inject
    IBEUserBusinessLogic userLogic;

    private static final Logger logger = LoggerFactory.getLogger(UserActor.class);

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof Request) {
            Request request = (Request) message;
            logger.debug("received message! Action: {}", request.getAction());
            switch (request.getAction()) {
                case GET:
                    userLogic.find((List<String>) request.getMessage());
                    break;
                case DELETE:
                    userLogic.delete((List<String>) request.getMessage());
                    break;
                case SAVE:
                    userLogic.save((List<User>) request.getMessage());
                    break;
                case UPDATE:
                    userLogic.update((List<User>) request.getMessage());
                    break;
                default:
                    logger.error("non valid action");
            }
            getSender().tell("done!", getSelf());
        } else {
            logger.error("unhandled message");
            unhandled(message);
        }
    }

}