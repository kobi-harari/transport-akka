package com.nils.entities.transport;

import java.io.Serializable;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class Error implements Serializable {
    int errorCode;
    String message;
    Throwable throwable;
}
