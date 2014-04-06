package com.akka.actor.logic;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.akka.entity.SendMessageAttributes;
import com.akka.interfaces.IBEUserBusinessLogic;
import com.akka.system.IocInitializer;
import com.google.inject.Injector;
import com.nils.entities.Account;
import com.nils.entities.User;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


/**
 * User actor for user crud operations
 * Created by uri.silberstein on 4/3/14.
 */
public class MockActor extends UntypedActor {

    private static final Logger logger = LoggerFactory.getLogger(MockActor.class);


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
                    String type = request.getService();
                    if(type.equals("User")){
                        User user1 = new User("1", "Kobi", 40, "111");
                        User user2 = new User("2", "Uri", 40, "111");
                        List<User> users = new LinkedList<>();
                        users.add(user1);
                        users.add(user2);
                        response.setMessage((Serializable)users);
                    }
                    if(type.equals("Account")){
                        Account account1 = new Account("111", "MM-account");
                        Account account2 = new Account("222", "another-account");
                        List<Account> accounts = new LinkedList<>();
                        accounts.add(account1);
                        accounts.add(account2);
                        response.setMessage((Serializable)accounts);
                    }
                    break;
                case DELETE:
                    break;
                case SAVE:
                    break;
                case UPDATE:
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