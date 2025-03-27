package br.com.fabianorodrigo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;

public class App {

    public static void main(String[] args) {

        String url = args.length > 0 ? args[0] : "http://localhost:3000";
        String body = args.length > 1 ? args[1]
                : "{\"count\":5, \"ackmode\":\"ack_requeue_true\", \"encoding\":\"auto\"}";
        String method = args.length > 2 ? args[2] : "POST";
        String username = args.length > 3 ? args[3] : "guest";
        String password = args.length > 4 ? args[4] : "guest";

        String userCredentials = String.format("%s:%s", username, password);
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url)).header("Authorization", basicAuth)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        // CHAT GPT sugeriu forçar o forçar o HTTP/1.1 a fim de evitar problemas se o
        // RabbitMQ não suportar bem HTTP/2.
        // Isso resolveu problema recorrente: java.io.IOException: EOF reached while
        // reading
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10)).version(HttpClient.Version.HTTP_1_1) // Forçar HTTP/1.1
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        String.format("Falha na requisição %s %s. HTTP %s: %s", url, method, response.statusCode(),
                                response.body()));
            }
            System.out.println(String.format("Requisição %s %s enviada com sucesso. HTTP %s: %s", url, method,
                    response.statusCode(), response.body()));
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "InterruptedException ao enviar a requisição: " + e.getMessage(), e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IOException ao enviar a requisição: " + e.getMessage(),
                    e);
        } finally {
            // // Finaliza corretamente o ExecutorService
            // executor.shutdown();
            // try {
            // if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            // executor.shutdownNow();
            // }
            // } catch (InterruptedException e) {
            // executor.shutdownNow();
            // }
            // No need to close HttpClient as it does not have a close method
        }
        System.exit(0);
    }
}
