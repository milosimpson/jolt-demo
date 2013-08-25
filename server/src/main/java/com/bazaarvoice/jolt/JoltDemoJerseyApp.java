package com.bazaarvoice.jolt;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Create a small Jersey App that defines the Jersey Annotated class that we want it to
 *  inspect and run.
 */
public class JoltDemoJerseyApp extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>() {{
            // Add your resources.
            add(JoltTransformServlet.class);

            // Add LoggingFilter.
//            add(LoggingFilter.class);
        }};
    }
}
