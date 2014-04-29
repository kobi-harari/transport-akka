package com.akka.actor.logic.orchestration;

/**
 * Created by uri.silberstein on 4/29/14.
 */
public class AttachUserAccountMessage {

    final private String userId;
    final private String accountId;

    public AttachUserAccountMessage(String userId, String accountId) {
        this.userId = userId;
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public String getAccountId() {
        return accountId;
    }
}
