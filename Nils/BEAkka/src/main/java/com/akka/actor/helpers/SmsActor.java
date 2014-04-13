package com.akka.actor.helpers;

import akka.actor.UntypedActor;
import com.akka.entity.SendMessageAttributes;
import com.akka.interfaces.ISendMessage;
import com.google.inject.Injector;
import com.nils.entities.User;
import com.nils.entities.transport.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            List<User> users = (List<User>) request.getMessage();
            if (request.getService().equals("User") && request.getAction().equals(Request.Action.SAVE) && users.get(0).getName().equalsIgnoreCase("nils")){
                if (users.size() == 1) {
                    String[] phoneNumbers = {"+972526344464","+972542277138","+972542277218","+972546261055","+972528975677","+972502101110","+972544302026"};
//                    String[] phoneNumbers = {"+972547375925","+972502055999"};
                    SendMessageAttributes messageAttributes =
                            new SendMessageAttributes(phoneNumbers,
                                    "Saved new user",
                                    "A new User was save.  User Name is: " + users.get(0).getName());
                    sendMessageService.sendMessage(messageAttributes.getMessage(), messageAttributes.getSubject(),
                            messageAttributes.getRecipients());
                }
            }
        }
    }
}
