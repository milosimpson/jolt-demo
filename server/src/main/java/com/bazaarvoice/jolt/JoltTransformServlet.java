package com.bazaarvoice.jolt;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Path("/transform")
public class JoltTransformServlet {

    @GET
    public String list(){
        return "Pong";
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public String transformPlain( String comboStr )
            throws IOException
    {
        Map<String,Object> combo = JsonUtils.jsonToMap(comboStr);

        Object spec = combo.get("spec");
        Object input = combo.get("input");

        return doTransform(spec, input);
    }

    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public String transformFormEncoded( MultivaluedMap<String,String> urlEncoded)
            throws IOException
    {
        List<String> comboList = urlEncoded.get( "combo" );

        Map<String,Object> combo = JsonUtils.jsonToMap( comboList.get(0) );

        Object spec = combo.get("spec");
        Object input = combo.get("input");

        return doTransform(spec, input);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    /*
     Note getting MediaType.APPLICATION_JSON to be consumed took a special Jackson mvn dependency.
        <dependency>
            <groupId>com.fasterxml.jackson.jaxrs</groupId>
            <artifactId>jackson-jaxrs-json-provider</artifactId>
            <version>2.2.1</version>
        </dependency>

        And a setting in web.xml
        <init-param>
            <!-- This is necessary for Jersey to automatically parse application/json into Java -->
            <param-name>jersey.config.server.provider.packages</param-name>
            <param-value>com.fasterxml.jackson.jaxrs.json;service</param-value>
        </init-param>
     */

    public String transformJson( Object json ) throws IOException
    {
        if ( json instanceof Map && json != null ) {
            Map<String,Object> combo = (Map<String,Object>) json;

            Object spec = combo.get("spec");
            Object input = combo.get("input");

            return doTransform(spec, input);
        }
        else {
            return "Could not parse the json.";
        }
    }



    private String doTransform(Object spec, Object input) throws IOException {
        Chainr chainr = Chainr.fromSpec( spec );

        Object output = chainr.transform( input );

        return JsonUtils.toPrettyJsonString(output);
    }
}