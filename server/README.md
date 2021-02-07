App Engine Java Application
Copyright (C) 2010-2012 Google Inc.

## Update 2021-02

Google Free Tier still exists, but they really really really want a credit card to bill.
So need to push an updated version of the app that says "only one instance ever".

### Work

Updated to Java11 appEngine that does not want to use a war file, and wants a DropWizard/SpringBoot
style main method class that starts jetty.

Followed https://cloud.google.com/appengine/docs/standard/java11/java-differences
And did minimal work.   
See also https://happycoding.io/tutorials/google-cloud/migrating-to-java-11

This entailed downloading a google repo that "main-ifys" the running of a War file.

#### Run the thing locally

```
# build the war
cd ~/code/milo/jolt-demo
mvn clean install

# run the war
~/code/milo/java-docs-samples/appengine-java11/appengine-simple-jetty-main
mvn exec:java -Dexec.args="../../../jolt-demo/server/target/server-0.0.1-SNAPSHOT.war"
```

#### Deploy

```
edit this part in the server/pom.xml part to make a new version

<plugin>
    <groupId>com.google.cloud.tools</groupId>
    <artifactId>appengine-maven-plugin</artifactId>
    <version>2.2.0</version>
    <configuration>
        <projectId>jolt-demo</projectId>
        <version>15</version>
    </configuration>
</plugin>
            


cd server

mvn clean package appengine:deploy
```


## Skeleton application for use with App Engine Java.

Java should be 1.8.

    export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)

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

