package com.bazaarvoice.jolt;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("jolt")
public class JerseyHelloWorld {

    // Plain text works!
    @GET
    // @Consumes(MediaType.TEXT_PLAIN)
    public String list(){
        return "Got it!";
    }
}