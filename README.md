# java-http-client
Um cliente HTTP simples destinado apenas a testes


Para executar:

```shell
mvn package && java -cp target/java-http-client-1.0-SNAPSHOT.jar br.com.fabianorodrigo.App 
```

OU, como a configuração do `maven-jar-plugin` define um MANIFEST:

```shell
mvn package && java -jar target/java-http-client-1.0-SNAPSHOT.jar
```
