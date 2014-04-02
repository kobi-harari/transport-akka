package com.nils.feimpl;

import com.nils.entities.Account;
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
