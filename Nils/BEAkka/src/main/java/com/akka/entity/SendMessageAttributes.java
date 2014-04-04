package com.akka.entity;

/**
 * Created by kobi on 4/5/14.
 */
public class SendMessageAttributes {
    String [] recipients;
    String subject;
    String message;

    public SendMessageAttributes(String[] recipients, String subject, String message) {
        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
    }

    public String[] getRecipients() {
        return recipients;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
}
