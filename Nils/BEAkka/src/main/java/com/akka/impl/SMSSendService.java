package com.akka.impl;

import com.akka.interfaces.IConfigurationHolder;
import com.akka.interfaces.ISendMessage;
import com.nexmo.messaging.sdk.NexmoSmsClient;
import com.nexmo.messaging.sdk.messages.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kobi on 4/3/14.
 */
public class SMSSendService implements ISendMessage {
    private static final Logger logger = LoggerFactory.getLogger(SMSSendService.class);
    NexmoSmsClient smsClient;

    public SMSSendService() {
        IConfigurationHolder configurationHolder = new ConfigurationHolder();
        try {
            logger.info("creating SMSSendService");
            smsClient = new NexmoSmsClient(configurationHolder.getValue("sms.api.key"), configurationHolder.getValue("sms.api.api"));
        } catch (Exception e) {
            logger.error("could not init the sms client", e);
        }
    }

    @Override
    public void sendMessage(String message, String subject, String... recipients) {
        logger.debug("Going to send sms message to all recipients: " + recipients);
        TextMessage tMessage;
        for (String recipient : recipients) {
            logger.info("Message is about to be sent to {} with message {}", recipient, message);
            tMessage = new TextMessage("+972502055999", recipient, message);
            try {
                if (smsClient != null) {
                    smsClient.submitMessage(tMessage);
                }else{
                    logger.error("can't send messages the client is null");
                }

            } catch (Exception e) {
                logger.error("could not send message", e);
            }
        }


    }
}


