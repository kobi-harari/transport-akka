package com.akka.system;

import com.akka.impl.SMSSendService;
import com.akka.interfaces.ISendMessage;
import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Created by kobi on 4/3/14.
 */
public class SystemModule implements Module {
    @Override
    public void configure(Binder binder) {
          binder.bind(ISendMessage.class).to(SMSSendService.class);
    }
}
