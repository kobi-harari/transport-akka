package com.akka.actor.logic;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.akka.actor.helpers.SmsActor;
import com.akka.entity.SendMessageAttributes;
import com.akka.interfaces.IBEAccountBusinessLogic;
import com.akka.interfaces.IBEUserBusinessLogic;
import com.akka.system.IocInitializer;
import com.google.inject.Injector;
import com.nils.entities.Account;
import com.nils.entities.User;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.util.logging.resources.logging;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kobi on 4/8/14.
 */
public class AccountActor extends UntypedActor {
    private static final Logger logger = LoggerFactory.getLogger(AccountActor.class);
    IBEAccountBusinessLogic accountLogic;

    public AccountActor(Injector injector) {
        accountLogic = injector.getInstance(IBEAccountBusinessLogic.class);
    }

    @Override
    public void preStart() {
        // If we don't get any progress within 15 seconds then the service
        // is unavailable
        logger.info("Account Actor preStart");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Request) {
            Request request = (Request) message;
            Response response = new Response(request.getMetaData(),request.getService(),request.getAction());
            logger.debug("received message! Action: {}", request.getAction());
            switch (request.getAction()) {
                case GET:
                    List<Account> accounts = accountLogic.find((List<String>) request.getMessage());
                    response.setMessage((Serializable)accounts);
                    break;
                case DELETE:
                    accountLogic.delete((List<String>) request.getMessage());
                    break;
                case SAVE:
                    accountLogic.save((List<Account>) request.getMessage());
                    break;
                case UPDATE:
                    accountLogic.update((List<Account>) request.getMessage());
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
