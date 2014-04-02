package com.akka.actor;

import akka.actor.UntypedActor;
import com.akka.interfaces.ISendMessage;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Created by kobi on 4/3/14.
 */
public class Sms extends UntypedActor {
    @Inject
    private ISendMessage sendMessageService;

    public Sms(Injector injector) {
        sendMessageService = injector.getInstance(ISendMessage.class);
    }

    @Override
    public void onReceive(Object o) throws Exception {
        // check the messag and send sms
        String [] recipients  = {"0502055999"};
        sendMessageService.sendMessage("This is a message from akka acptor", "a message from akka",recipients);
    }
}
