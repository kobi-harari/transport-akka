package com.transport.logic.user;

import com.nils.entities.Account;
import com.nils.entities.User;
import com.nils.entities.transport.MetaData;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import com.transport.logic.base.FEBusinessLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.util.logging.resources.logging;

import java.util.Arrays;
import java.util.List;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class UserBusinessLogic extends FEBusinessLogic<User, String> implements IUserBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(UserBusinessLogic.class);

    public UserBusinessLogic() {
        super(User.class);
    }


    @Override
    public void attacheUserToAccount(String userId, String accountId) {

        Request userRequest = new Request(new MetaData(), "User", Request.Action.GET, userId);
        Request accountRequest = new Request(new MetaData(), "Account", Request.Action.GET, accountId);
        List<Response> responses = orchestrate(Arrays.asList(userRequest, accountRequest));

        User user = null;
        for(Response response : responses){
            if(response.getService().equals("User")){
                user = (User)response.getMessage();
                logger.info("old user accountId is: {}", user.getAccountId() );
            }
            if(response.getService().equals("Account")){
                Account account = (Account)response.getMessage();
                logger.info("Validated that the account exists");
                user.setAccountId(account.getId());
            }
        }

        update(Arrays.asList(user));
    }
}
