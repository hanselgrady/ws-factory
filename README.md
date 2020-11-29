# ws-factory

web service factory

Requires maven

# Running
```
    mvn package
    mvn clean compile assembly:single
    java -jar .\target\jax-ws-soap-1.0-SNAPSHOT-jar-with-dependencies.jar
    java -cp .\target\jax-ws-soap-1.0-SNAPSHOT-jar-with-dependencies.jar jax.ws.factory.App
```