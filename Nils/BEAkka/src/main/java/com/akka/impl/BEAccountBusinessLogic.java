package com.akka.impl;

import com.akka.interfaces.IAccountOperation;
import com.akka.interfaces.IBEAccountBusinessLogic;
import com.google.inject.Inject;

/**
 * Created by kobi on 4/8/14.
 */
public class BEAccountBusinessLogic  <Account, String> extends BEBaseBusinessLogic implements IBEAccountBusinessLogic {
    @Inject
    public BEAccountBusinessLogic(IAccountOperation accountOperation) {
        super(accountOperation);
    }
}
