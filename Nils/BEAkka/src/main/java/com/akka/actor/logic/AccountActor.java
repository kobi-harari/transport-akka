package com.akka.actor.logic;

import akka.actor.UntypedActor;
import com.akka.interfaces.IBEUserBusinessLogic;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kobi on 4/8/14.
 */
public class AccountActor extends UntypedActor {
    private static final Logger logger = LoggerFactory.getLogger(AccountActor.class);
    IBEUserBusinessLogic accountLogic;

    public AccountActor(Injector injector) {
        accountLogic = injector.getInstance(IBEUserBusinessLogic.class);
    }
    public AccountActor(IBEUserBusinessLogic accountLogic) {
        this.accountLogic = accountLogic;
    }

    @Override
    public void onReceive(Object o) throws Exception {

    }
}
