package com.bazaarvoice.jolt;

import org.glassfish.jersey.filter.LoggingFilter;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class JoltDemoJerseyApp extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>() {{
            // Add your resources.
            add(JerseyHelloWorld.class);

            // Add LoggingFilter.
//            add(LoggingFilter.class);
        }};
    }
}
