# java-http-client
Um cliente HTTP simples destinado apenas a testes

## Configuração

Confirme que o path da JDk está correta no arquivo `.vscode/settings.json`.


## Execução

```shell
mvn package && java -cp target/java-http-client-1.0-SNAPSHOT.jar br.com.fabianorodrigo.App [url] [body] [method] [username] [password]
```

OU, como a configuração do `maven-jar-plugin` define um MANIFEST:

```shell
mvn package && java -jar target/java-http-client-1.0-SNAPSHOT.jar [url] [body] [method] [username] [password]
```
