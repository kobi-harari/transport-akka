package com.transport.logic.user;

import com.google.inject.Inject;
import com.nils.entities.Account;
import com.nils.entities.User;
import com.nils.entities.transport.MetaData;
import com.nils.entities.transport.Request;
import com.nils.entities.transport.Response;
import com.nils.interfaces.IBaseBusinessLogic;
import com.transport.logic.base.FEBusinessLogic;
import com.transport.logic.transport.ITransportLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.util.logging.resources.logging;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class UserBusinessLogic extends FEBusinessLogic<User, String> implements IUserBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(UserBusinessLogic.class);

    @Inject
    public UserBusinessLogic(ITransportLayer transportLayer) {
        super(transportLayer);
        setClazz(User.class);
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

    @Override
    public void getUsersAndAccount(List<String> userIds, List<String> accountIds, List<User> users, List<Account> accounts) {
        Request userRequest = new Request(new MetaData(), "User", Request.Action.GET, (Serializable)userIds);
        Request accountRequest = new Request(new MetaData(), "Account", Request.Action.GET, (Serializable)accountIds);
        List<Response> responses = orchestrate(Arrays.asList(userRequest, accountRequest));

        for(Response response : responses){
            if(response.getService().equals("User")){
                users.addAll((List<User>)response.getMessage());
            }
            if(response.getService().equals("Account")){
                logger.info("Validated that the account exists");
                accounts.addAll((List<Account>)response.getMessage());
            }
        }
    }
}
