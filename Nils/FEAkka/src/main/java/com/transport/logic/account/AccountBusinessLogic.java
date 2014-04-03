package com.transport.logic.account;

import com.nils.entities.Account;
import com.transport.logic.base.BaseBusinessLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class AccountBusinessLogic extends BaseBusinessLogic<Account, String> {

    private static final Logger logger = LoggerFactory.getLogger(AccountBusinessLogic.class);

    public AccountBusinessLogic() {
        super(Account.class);
    }


}
