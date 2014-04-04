package com.akka.actor;

import akka.actor.UntypedActor;
import com.akka.entity.SendMessageAttributes;
import com.akka.interfaces.ISendMessage;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kobi on 4/3/14.
 */
public class SmsActor extends UntypedActor {
    private static final Logger logger = LoggerFactory.getLogger(SmsActor.class);

    private ISendMessage sendMessageService;

    public SmsActor(Injector injector) {
        sendMessageService = injector.getInstance(ISendMessage.class);
    }

    @Override
    public void onReceive(Object o) throws Exception {
        logger.debug("SmsActor was called to send a message");
        if (o instanceof SendMessageAttributes){
            SendMessageAttributes messageAttributes = (SendMessageAttributes)o;
            sendMessageService.sendMessage(messageAttributes.getSubject(), messageAttributes.getMessage(),
                    messageAttributes.getRecipients());
            logger.debug("message with subject {} was sent to {}", messageAttributes.getSubject(),messageAttributes.getRecipients());
        }
    }
}
