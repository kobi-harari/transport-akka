package com.akka.actor.logic;

import akka.actor.UntypedActor;
import com.akka.interfaces.IBEOrderItemBusinessLogic;
import com.google.inject.Injector;
import com.nils.entities.OrderItem;
import com.nils.entities.User;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kobi on 4/15/14.
 */
public class OrderItemActor extends UntypedActor {
    private static final Logger logger = LoggerFactory.getLogger(OrderItemActor.class);
    IBEOrderItemBusinessLogic orderItemBusinessLogic;

    public OrderItemActor(Injector injector) {
        this.orderItemBusinessLogic = injector.getInstance(IBEOrderItemBusinessLogic.class);
    }

    @Override
    public void preStart() {
        // If we don't get any progress within 15 seconds then the service
        // is unavailable
        logger.debug("OrderItemActor preStart");
    }


    @Override
    public void onReceive(Object message) throws Exception {
        logger.debug("got a message into the OrderItemActor");
        if (message instanceof Request) {
            Request request = (Request) message;
            Response response = new Response(request.getMetaData(), request.getService(), request.getAction());
            logger.debug("received message! Action: {}", request.getAction());
            switch (request.getAction()) {
                case GET:
                    List<OrderItem> orderItems = orderItemBusinessLogic.find((List<String>) request.getMessage());
                    System.out.println(" id: " + ((List<String>) request.getMessage()).get(0) + " Actor: " + this);
                    response.setMessage((Serializable) orderItems);
                    break;

            }
        getSender().tell(response, getSelf());
        }
    }
}