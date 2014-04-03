package com.akka.logic.actors;

import akka.actor.UntypedActor;
import com.akka.interfaces.IBEUserBusinessLogic;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nils.entities.User;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;


/**
 * Created by uri.silberstein on 4/3/14.
 */
public class UserActor extends UntypedActor {

    private static final Logger logger = LoggerFactory.getLogger(UserActor.class);
    IBEUserBusinessLogic userLogic;

    public UserActor(Injector injector) {
        userLogic = injector.getInstance(IBEUserBusinessLogic.class);
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof Request) {
            Request request = (Request) message;
            Response response = new Response(request.getMetaData(),request.getService(),request.getAction());
            logger.debug("received message! Action: {}", request.getAction());
            switch (request.getAction()) {
                case GET:
                    List<User> users = userLogic.find((List<String>) request.getMessage());
                    response.setMessage((Serializable)users);
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
            getSender().tell(response, getSelf());
        } else {
            logger.error("unhandled message");
            unhandled(message);
        }
    }

}