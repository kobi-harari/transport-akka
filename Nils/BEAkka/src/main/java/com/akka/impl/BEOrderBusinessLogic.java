package com.akka.impl;

import com.akka.interfaces.IBEOrderBusinessLogic;
import com.akka.interfaces.IOrderOperation;
import com.google.inject.Inject;

/**
 * Created by kobi on 4/15/14.
 */
public class BEOrderBusinessLogic extends BEBaseBusinessLogic implements IBEOrderBusinessLogic {
    @Inject
    public BEOrderBusinessLogic(IOrderOperation dbOperations) {
        super(dbOperations);
    }
}
