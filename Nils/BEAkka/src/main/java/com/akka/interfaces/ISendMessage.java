package com.akka.interfaces;

/**
 * Created by kobi on 4/3/14.
 */
public interface ISendMessage {
    void sendMessage(String message,String subject, String...recipients);
}
