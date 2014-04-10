package com.transport.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nils.entities.BaseEntity;
import com.nils.entities.User;
import com.nils.utils.IJsonTranslator;
import com.transport.ioc.SystemModule;
import com.transport.logic.user.IUserBusinessLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;

/**
 * Created by kobi on 3/27/14.
 */
@Singleton
@Path("/user")
public class FEUser {

    private static final Logger logger = LoggerFactory.getLogger(FEUser.class);

    //    @Inject
    IUserBusinessLogic userLogic;
    IJsonTranslator<String> jsonSerilizer;

    public FEUser() {
        System.out.println("This is not Nils Holgerson but we are going to talk to Akka about it");
        System.out.println("aaa");
        Injector injector = Guice.createInjector(new SystemModule());
        userLogic = injector.getInstance(IUserBusinessLogic.class); //TODO AutoInject by Jersey context
        jsonSerilizer = injector.getInstance(IJsonTranslator.class);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getById(@Suspended final AsyncResponse asyncResponse, @PathParam("id") final String id) {
        logger.debug("get User by id: {}", id);
        asyncResponse.resume(jsonSerilizer.encode(userLogic.find(Arrays.asList(id))));
    }

    @DELETE
    @Path("/{id}")
    public void deleteById(@Suspended final AsyncResponse asyncResponse, @PathParam("id") final String id) {
        logger.debug("delete User by id: {}", id);
        userLogic.delete(Arrays.asList(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public void save(@Suspended final AsyncResponse asyncResponse, String userStr) {
        User user = (User) jsonSerilizer.decode(userStr, User.class);
        logger.debug("save User: {}", userStr);
        userLogic.save(Arrays.asList(user));
        asyncResponse.resume("User was saved!");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/test")
    public void saveaaaa(@Suspended final AsyncResponse asyncResponse, User user) {
        logger.debug("save User: {}", user);
        userLogic.save(Arrays.asList(user));
        asyncResponse.resume("User was saved!");
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@Suspended final AsyncResponse asyncResponse, @PathParam("id") final String id, final User user) {
        logger.debug("update User: {}", user);
        userLogic.update(Arrays.asList(user));
    }
}
