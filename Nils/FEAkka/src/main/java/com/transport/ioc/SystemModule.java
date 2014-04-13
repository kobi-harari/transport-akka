package com.transport.ioc;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.nils.entities.BaseEntity;
import com.nils.interfaces.IBaseBusinessLogic;
import com.nils.utils.GsonTranslator;
import com.nils.utils.IJsonTranslator;
import com.nils.utils.JacksonTranslator;
import com.transport.logic.account.AccountBusinessLogic;
import com.transport.logic.account.IAccountBusinessLogic;
import com.transport.logic.base.FEBusinessLogic;
import com.transport.logic.transport.FEAkkaTransport;
import com.transport.logic.transport.ITransportLayer;
import com.transport.logic.user.IUserBusinessLogic;
import com.transport.logic.user.UserBusinessLogic;

/**
 * Created by uri.silberstein on 4/3/14.
 */
public class SystemModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(ITransportLayer.class).to(FEAkkaTransport.class);
        binder.bind(IUserBusinessLogic.class).to(UserBusinessLogic.class);
        binder.bind(IAccountBusinessLogic.class).to(AccountBusinessLogic.class);
        binder.bind(IJsonTranslator.class).to(JacksonTranslator.class);
    }
}
