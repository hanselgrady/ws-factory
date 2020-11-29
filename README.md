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

#Skema Basis Data
<br>choco_stock = (**chocoid**, choconame, amount)<br>
ingredients = (**ingredientsid**, ingredientsname, ingredientsamount, expiredate)<br>
recipe = (***chocoid***, ***ingredientsid***, ingredientsamount)<br>
request = (**requestid**, chocoid, amount, status)<br>
saldo = (**saldoid**, saldoamount)<br>
