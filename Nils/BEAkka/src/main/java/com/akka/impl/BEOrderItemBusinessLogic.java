package com.akka.impl;

import com.akka.interfaces.IBEOrderItemBusinessLogic;
import com.akka.interfaces.IOrderItemOperation;
import com.google.inject.Inject;

/**
 * Created by kobi on 4/16/14.
 */
public class BEOrderItemBusinessLogic extends BEBaseBusinessLogic implements IBEOrderItemBusinessLogic {
    @Inject
    public BEOrderItemBusinessLogic(IOrderItemOperation dbOperations) {
        super(dbOperations);
    }
}
