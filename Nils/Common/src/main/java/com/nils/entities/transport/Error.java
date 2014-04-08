package com.nils.entities.transport;

import java.io.Serializable;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class Error implements Serializable {
    final int errorCode;
    final String message;
    final Throwable throwable;

    public static int TRANSPORT_ERROR = 1;
    public static int SERVICE_NOT_AVAILABLE = 2;
    public static int ACTION_NOT_VALID = 3;
    public static int ACTION_MISSING = 4;

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
