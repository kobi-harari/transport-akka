package com.transport.service;

import com.nils.entities.User;
import com.transport.logic.user.IUserBusinessLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

/**
 * Created by kobi on 3/27/14.
 */
@Path("/user")
public class FEUser {

    private static final Logger log = LoggerFactory.getLogger(FEUser.class);

    IUserBusinessLogic userLogic;

    public FEUser() {
        System.out.println("This is not Nils Holgerson but we are going to talk to Akka about it");
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getById(@Suspended final AsyncResponse asyncResponse, @PathParam("id") final String id) {
        return new User("", "",1, "");
    }
}
