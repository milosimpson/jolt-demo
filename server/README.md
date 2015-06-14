App Engine Java Application
Copyright (C) 2010-2012 Google Inc.

## Skeleton application for use with App Engine Java.

Java should be 1.7.

    export JAVA_HOME=$(/usr/libexec/java_home -v 1.7)

Requires [Apache Maven](http://maven.apache.org) 3.0 or greater, and JDK 6+ in order to run.

To build, run

    mvn package

Building will run the tests, but to explicitly run tests you can use the test target

    mvn test

To start the app, use the [App Engine Maven Plugin](http://code.google.com/p/appengine-maven-plugin/) that is already included in this demo.  Just run the command.

    mvn appengine:devserver
    # To see the base hello world servlet
    http://localhost:8080

For further information, consult the [Java App Engine](https://developers.google.com/appengine/docs/java/overview) documentation.

To see all the available goals for the App Engine plugin, run

    mvn help:describe -Dplugin=appengine

# Build and Test

You can run Google App Engine from inside Intellij.
Need to change the config to run "mvn clean" and "mvn verify" instead of its usual "Make"

# Deploy

Do a "mvn clean install" from the top, otherwise the "${appengine.app.version}" from the "appengine-web.xml" does not get filled in.
Deploy from inside Intellij.

Google will let your run multiple versions of the same app, which this demo will not need.

# Reference

https://developers.google.com/appengine/docs/java/tools/maven

http://jersey.java.net/

http://stackoverflow.com/questions/2072295/how-to-deploy-a-jax-rs-application

http://stackoverflow.com/questions/18268827/how-do-i-get-jersey-2-2-jax-rs-to-generate-log-output-including-json-request

Jboss guy article
http://architects.dzone.com/articles/putting-java-rest

