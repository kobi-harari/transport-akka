package com.akka.actor.logic;

import akka.actor.UntypedActor;
import com.akka.interfaces.IBEOrderBusinessLogic;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public void onReceive(Object o) throws Exception {

    }
}
