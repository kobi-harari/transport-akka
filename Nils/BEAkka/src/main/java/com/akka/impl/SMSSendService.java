package com.akka.impl;

import com.akka.interfaces.ISendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kobi on 4/3/14.
 */
public class SMSSendService implements ISendMessage {
    private static final Logger logger = LoggerFactory.getLogger(SMSSendService.class);

    @Override
    public void sendMessage(String message, String subject, String... recipients) {
        logger.debug("going to send sms message to all recipients: " + recipients);

    }
}
