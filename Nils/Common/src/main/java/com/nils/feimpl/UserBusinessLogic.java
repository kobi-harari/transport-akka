package com.nils.feimpl;

import com.nils.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public class UserBusinessLogic extends BaseBusinessLogic<User, String> {

    private static final Logger logger = LoggerFactory.getLogger(UserBusinessLogic.class);

    public UserBusinessLogic() {
        super(User.class);
    }


}
