package com.nils.entities.transport;

import java.io.Serializable;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class Error implements Serializable {
    final int errorCode;
    final String message;
    final Throwable throwable;

    public Error(int errorCode, String message, Throwable throwable) {
        this.errorCode = errorCode;
        this.message = message;
        this.throwable = throwable;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
