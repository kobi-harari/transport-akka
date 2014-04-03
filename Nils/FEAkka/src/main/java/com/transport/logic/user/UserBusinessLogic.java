package com.transport.logic.user;

import com.nils.entities.User;
import com.transport.logic.base.BaseBusinessLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class UserBusinessLogic extends BaseBusinessLogic<User, String> implements IUserBusinessLogic {

    public UserBusinessLogic() {
        super(User.class);
    }


}
