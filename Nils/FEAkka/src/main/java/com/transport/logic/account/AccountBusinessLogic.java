package com.transport.logic.account;

import com.google.inject.Inject;
import com.nils.entities.Account;
import com.nils.entities.User;
import com.transport.logic.base.FEBusinessLogic;
import com.transport.logic.transport.ITransportLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class AccountBusinessLogic extends FEBusinessLogic<Account, String> implements IAccountBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(AccountBusinessLogic.class);

    @Inject
    public AccountBusinessLogic(ITransportLayer transportLayer) {
        super(transportLayer);
        setClazz(Account.class);
    }

}
