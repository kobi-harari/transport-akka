package com.akka.actor.helpers;

import akka.actor.UntypedActor;
import com.akka.entity.SendMessageAttributes;
import com.akka.interfaces.ISendMessage;
import com.google.inject.Injector;
import com.nils.entities.User;
import com.nils.entities.transport.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

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
        if (o instanceof Request) {
            Request request = (Request) o;
            if (request.getService().equals("User") && request.getAction().equals(Request.Action.SAVE)){
                List<User> users = (List<User>) request.getMessage();
                if (users.size() == 1) {
                    String[] phoneNumbers = {"+972502055999"};
                    SendMessageAttributes messageAttributes =
                            new SendMessageAttributes(phoneNumbers,
                                    "Saved new user",
                                    "A new User was save.  User Name is: " + users.get(0).getName());
                    sendMessageService.sendMessage(messageAttributes.getMessage(), messageAttributes.getSubject(),
                            messageAttributes.getRecipients());

                }
            }
        }
//        if (o instanceof SendMessageAttributes) {
//            SendMessageAttributes messageAttributes = (SendMessageAttributes) o;
//            sendMessageService.sendMessage(messageAttributes.getMessage(), messageAttributes.getSubject(),
//                    messageAttributes.getRecipients());
//            logger.debug("message with subject {} was sent to {}", messageAttributes.getSubject(), messageAttributes.getRecipients());
//        }
    }
}
