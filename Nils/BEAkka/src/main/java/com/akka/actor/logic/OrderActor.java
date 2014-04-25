package com.akka.actor.logic;

import akka.actor.UntypedActor;
import com.akka.interfaces.IBEOrderBusinessLogic;
import com.google.inject.Injector;
import com.nils.entities.Order;
import com.nils.entities.OrderItem;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kobi on 4/15/14.
 */
public class OrderActor extends UntypedActor {
    private static final Logger logger = LoggerFactory.getLogger(OrderActor.class);
    IBEOrderBusinessLogic orderBusinessLogic;
    public OrderActor(Injector injector) {
        orderBusinessLogic = injector.getInstance(IBEOrderBusinessLogic.class);
    }

    @Override
    public void preStart() {
        // If we don't get any progress within 15 seconds then the service
        // is unavailable
        logger.debug("OrderActor preStart");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        logger.debug("got a message into the OrderActor");
        if (message instanceof Request) {
            Request request = (Request) message;
            Response response = new Response(request.getMetaData(), request.getService(), request.getAction());
            logger.debug("received message! Action: {}", request.getAction());
            switch (request.getAction()) {
                case GET:
                    List<Order> orderItems = orderBusinessLogic.find((List<String>) request.getMessage());
                    System.out.println(" id: " + ((List<String>) request.getMessage()).get(0) + " Actor: " + this);
                    response.setMessage((Serializable) orderItems);
                    break;

            }
            getSender().tell(response, getSelf());
        }
    }
}
