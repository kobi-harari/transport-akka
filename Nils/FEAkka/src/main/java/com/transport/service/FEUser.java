package com.transport.service;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nils.entities.User;
import com.transport.ioc.SystemModule;
import com.transport.logic.user.IUserBusinessLogic;
import com.transport.logic.user.UserBusinessLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kobi on 3/27/14.
 */
@Path("/user")
public class FEUser {

    private static final Logger logger = LoggerFactory.getLogger(FEUser.class);

//    @Inject
    IUserBusinessLogic userLogic;

    public FEUser() {
        System.out.println("This is not Nils Holgerson but we are going to talk to Akka about it");
        System.out.println("aaa");
        Injector injector = Guice.createInjector(new SystemModule());
        userLogic = injector.getInstance(IUserBusinessLogic.class); //TODO AutoInject by Jersey context
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getById(@Suspended final AsyncResponse asyncResponse, @PathParam("id") final String id) {
        logger.debug("get User by id: {}",id);
        return userLogic.find(Arrays.asList(id));
    }

    @DELETE
    @Path("/{id}")
    public void deleteById(@Suspended final AsyncResponse asyncResponse, @PathParam("id") final String id) {
        logger.debug("delete User by id: {}",id);
        userLogic.delete(Arrays.asList(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void save(@Suspended final AsyncResponse asyncResponse, final User user) {
        logger.debug("save User: {}", user);
        userLogic.save(Arrays.asList(user));
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@Suspended final AsyncResponse asyncResponse, @PathParam("id") final String id, final User user) {
        logger.debug("update User: {}", user);
        userLogic.update(Arrays.asList(user));
    }
}
