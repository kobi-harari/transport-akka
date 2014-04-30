package com.akka.actor.logic;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.akka.actor.helpers.SmsActor;
import com.akka.entity.SendMessageAttributes;
import com.akka.interfaces.IBEUserBusinessLogic;
import com.akka.system.IocInitializer;
import com.google.inject.Injector;
import com.nils.entities.User;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import org.apache.commons.lang.UnhandledException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * User actor for user crud operations
 * Created by uri.silberstein on 4/3/14.
 */
public class UserActor extends UntypedActor {

    private static final Logger logger = LoggerFactory.getLogger(UserActor.class);
    IBEUserBusinessLogic userLogic;

    public UserActor(Injector injector) {
        userLogic = injector.getInstance(IBEUserBusinessLogic.class);
    }

    @Override
    public void preStart() {
        // If we don't get any progress within 15 seconds then the service
        // is unavailable
        logger.debug("UserActor preStart");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onReceive(Object message) throws Exception {
        // todo - if the action is not crud send the message to the BL for generalization
        if (message instanceof Request) {
            Request request = (Request) message;
            Response response = new Response(request.getMetaData(),request.getService(),request.getAction());
            logger.debug("received message! Action: {}", request.getAction());
            switch (request.getAction()) {
                case GET:
                    List<User> users = userLogic.find((List<String>) request.getMessage());
                    System.out.println(" id: " + ((List<String>) request.getMessage()).get(0) + " Actor: " + this);
                    response.setMessage((Serializable) users);
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
                case FIND_BY_PROPERTY:
                    users = userLogic.findByProperties((Map<String,Object>)request.getMessage());
                    response.setMessage((Serializable)users);
                default:
                    logger.error("non valid action");
            }
            getSender().tell(response, getSelf());
        } else {
            if(message instanceof User){
                throw new Exception("Error! un-handle type");
            }
            logger.error("unhandled message");
            unhandled(message);
        }
    }

}