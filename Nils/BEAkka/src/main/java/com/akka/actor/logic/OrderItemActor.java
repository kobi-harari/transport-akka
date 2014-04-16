package com.akka.actor.logic;

import akka.actor.UntypedActor;
import com.akka.interfaces.IBEOrderItemBusinessLogic;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public void onReceive(Object o) throws Exception {

    }
}
