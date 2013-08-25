package com.bazaarvoice.jolt;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.Map;

@Path("transform")
public class JerseyHelloWorld {

    // Plain text works!
    @GET
    // @Consumes(MediaType.TEXT_PLAIN)
    public String list(){
        return "Got it!";
    }

    // Plain text works!
    //@Consumes({"application/json"})
    @POST
    public String transform( String comboStr )
            throws IOException
    {
        Map<String,Object> combo = JsonUtils.jsonToMap(comboStr);

        Chainr chainr = Chainr.fromSpec( combo.get("spec") );

        Object output = chainr.transform( combo.get("input") );

        return JsonUtils.toPrettyJsonString( output );
    }
}