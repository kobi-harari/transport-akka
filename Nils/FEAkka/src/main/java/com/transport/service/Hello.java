package com.transport.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by kobi on 3/27/14.
 */
@Path("/ping")
public class Hello {
    public Hello() {
        System.out.println("This is not Nils Holgerson but we are going to talk to Akka about it");
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "Hey, This is Jersey JAX-RS !";
    }
}
