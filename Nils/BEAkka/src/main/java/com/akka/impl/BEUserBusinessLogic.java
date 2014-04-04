package com.akka.impl;

import com.akka.interfaces.DbOperations;
import com.akka.interfaces.IBEUserBusinessLogic;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class BEUserBusinessLogic<User, String> extends BEBaseBusinessLogic implements IBEUserBusinessLogic {
    public BEUserBusinessLogic(DbOperations dbOperations) {
        super(dbOperations);
    }
}
