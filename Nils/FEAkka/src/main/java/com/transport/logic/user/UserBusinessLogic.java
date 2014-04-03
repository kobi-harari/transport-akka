package com.transport.logic.user;

import com.nils.entities.User;
import com.transport.logic.base.FEBusinessLogic;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class UserBusinessLogic extends FEBusinessLogic<User, String> implements IUserBusinessLogic {

    public UserBusinessLogic() {
        super(User.class);
    }


}
