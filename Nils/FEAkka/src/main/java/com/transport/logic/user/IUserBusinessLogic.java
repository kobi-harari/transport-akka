package com.transport.logic.user;

import com.nils.entities.Account;
import com.nils.entities.User;
import com.nils.interfaces.IBaseBusinessLogic;

import java.util.List;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public interface IUserBusinessLogic extends IBaseBusinessLogic<User, String> {

    void attacheUserToAccount(String userId, String accountId);

    void getUsersAndAccount(List<String> userIds, List<String> accountIds, final List<User> users, List<Account> accounts);
}
