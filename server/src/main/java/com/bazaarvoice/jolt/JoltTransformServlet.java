package com.bazaarvoice.jolt;

import com.bazaarvoice.jolt.exception.JoltException;

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

    // THIS IS THE METHOD THAT THE WEBPAGE ACTUALLY CALLS
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public String transformFormEncoded( MultivaluedMap<String,String> urlEncoded)
            throws IOException
    {

        String inputString, specString;
        Boolean sort = Boolean.TRUE;

        try {
            List<String> inputList = urlEncoded.get( "input" );
            inputString = inputList.get( 0 );

            List<String> specList = urlEncoded.get( "spec" );
            specString = specList.get( 0 );

            List<String> sortList = urlEncoded.get( "sort" );
            sort = Boolean.valueOf( sortList.get( 0 ) );
        }
        catch ( Exception e ) {
            return  "Could not url-decode the inputs.\n";
        }

        Object input, spec;

        try {
            input = JsonUtils.jsonToObject( inputString );
        }
        catch ( Exception e ) {
            return  "Could not parse the 'input' JSON.\n";
        }

        try {
            spec = JsonUtils.jsonToObject( specString );
        }
        catch ( Exception e ) {
            return "Could not parse the 'spec' JSON.\n";
        }

        return doTransform( input, spec, sort );
    }


    // THIS IS A DECOY / The demo site does not call this
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public String transformPlain( String comboStr )
            throws IOException
    {
        Map<String,Object> combo = JsonUtils.jsonToMap(comboStr);

        Object spec = combo.get("spec");
        Object input = combo.get("input");

        return doTransform( input, spec, false );
    }


    // THIS IS A DECOY / The demo site does not call this
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

        This is if you want to POST a single JSON object.  It should have two keys, "input" and "spec".
     */
    public String transformJson( Object json ) throws IOException
    {
        if ( json instanceof Map && json != null ) {
            Map<String,Object> combo = (Map<String,Object>) json;

            Object spec = combo.get("spec");
            Object input = combo.get("input");

            return doTransform( input, spec, false );
        }
        else {
            return "Could not parse the json.";
        }
    }


    private String doTransform( Object input, Object spec, boolean doSort ) throws IOException {

        try {
            Chainr chainr = Chainr.fromSpec( spec );

            Object output = chainr.transform( input );

            if ( doSort ) {
                output = Sortr.sortJson( output );
            }

            return JsonUtils.toPrettyJsonString( output );
        }
        catch ( Exception e ) {

            StringBuilder sb = new StringBuilder();
            sb.append( "Error running the Transform.\n\n" );

            // Walk up the stackTrace printing the message for any JoltExceptions.
            Throwable exception = e;
            do {
                if ( exception instanceof JoltException ) {
                    sb.append( exception.getMessage() );
                    sb.append( "\n\n");
                }

                exception = exception.getCause();
            }
            while( exception != null );

            return sb.toString();
        }
    }
}