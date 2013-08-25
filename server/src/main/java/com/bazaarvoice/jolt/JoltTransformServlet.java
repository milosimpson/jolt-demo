package com.bazaarvoice.jolt;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.Map;

@Path("/transform")
public class JoltTransformServlet {

    // Plain text works!
    @GET
//    @Path("/getter")
    // @Consumes(MediaType.TEXT_PLAIN)
    public String list(){
        return "Getter";
    }

    // Plain text works!
    //@Consumes({"application/json"})
    @POST
//    @Path("/poster")
    public String transform( String comboStr )
            throws IOException
    {
        Map<String,Object> combo = JsonUtils.jsonToMap(comboStr);

        Chainr chainr = Chainr.fromSpec( combo.get("spec") );

        Object output = chainr.transform( combo.get("input") );

        return JsonUtils.toPrettyJsonString( output );
    }
}