package com.akka.impl;

import com.akka.interfaces.DbOperations;
import com.akka.interfaces.IBEUserBusinessLogic;
import com.google.inject.Inject;

import java.util.List;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class BEUserBusinessLogic<User, String> extends BEBaseBusinessLogic implements IBEUserBusinessLogic {
    @Inject
    public BEUserBusinessLogic(DbOperations dbOperations) {
        super(dbOperations);
    }
}
