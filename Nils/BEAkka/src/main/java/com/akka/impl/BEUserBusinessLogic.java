package com.akka.impl;

import com.akka.interfaces.IBEUserBusinessLogic;
import com.akka.interfaces.IUserOperation;
import com.google.inject.Inject;

import java.util.List;
import java.util.Map;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class BEUserBusinessLogic<User, String> extends BEBaseBusinessLogic implements IBEUserBusinessLogic {
    @Inject
    public BEUserBusinessLogic(IUserOperation userOperation) {
        super(userOperation);
    }

    @Override
    public List<User> findByProperties(Map propertise) {
        return dbOperations.findByProperties(propertise);
    }
}
