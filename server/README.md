App Engine Java Application
Copyright (C) 2010-2012 Google Inc.

## Skeleton application for use with App Engine Java.

Requires [Apache Maven](http://maven.apache.org) 3.0 or greater, and JDK 6+ in order to run.

To build, run

    mvn package

Building will run the tests, but to explicitly run tests you can use the test target

    mvn test

To start the app, use the [App Engine Maven Plugin](http://code.google.com/p/appengine-maven-plugin/) that is already included in this demo.  Just run the command.

    mvn appengine:devserver
    # To see the base hello world servlet
    http://localhost:8080/jolthello

For further information, consult the [Java App Engine](https://developers.google.com/appengine/docs/java/overview) documentation.

To see all the available goals for the App Engine plugin, run

    mvn help:describe -Dplugin=appengine

# Reference

https://developers.google.com/appengine/docs/java/tools/maven

http://jersey.java.net/

http://stackoverflow.com/questions/2072295/how-to-deploy-a-jax-rs-application

http://stackoverflow.com/questions/18268827/how-do-i-get-jersey-2-2-jax-rs-to-generate-log-output-including-json-request

Jboss guy article
http://architects.dzone.com/articles/putting-java-rest

