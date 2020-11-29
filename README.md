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

## Deskripsi Web Service Supplier.

Web Service Factory atau WS-Factory adalah web service yang melayani request dari Willy Wangky's Factory dan Willy Wangky's Web Application. WS-Supplier melayani di atas server jax-ws.

Saat pengguna menggunakan baik Willy Wangky's Factory maupun Willy Wangky's Web Application, pengguna akan menggunakan layanan ini untuk berhubungan dengan pabrik pembuatan coklat. Layanan ini berguna untuk penghubung dengan pabrik pembuatan coklat untuk produksi coklat yang akan dijual di Willy Wangky

Service yang disediakan adalah menyediakan informasi dan transaksi saldo, informasi bahan, pembuatan coklat, informasi resep, dan menangani permintaan dari Willy Wangky's Web Appliaction


# Skema Basis Data
<br>choco_stock = (**chocoid**, choconame, amount)<br>
ingredients = (**ingredientsid**, ingredientsname, ingredientsamount, expiredate)<br>
recipe = (***chocoid***, ***ingredientsid***, ingredientsamount)<br>
request = (**requestid**, chocoid, amount, status)<br>
saldo = (**saldoid**, saldoamount)<br>
