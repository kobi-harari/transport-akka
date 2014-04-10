package com.transport.jersey;

import com.transport.service.FEUser;
import com.transport.service.Hello;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kobi on 3/27/14.
 */
public class JerseyApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        // register root resource
        classes.add(Hello.class);
        classes.add(FEUser.class);
        return classes;
    }
}
