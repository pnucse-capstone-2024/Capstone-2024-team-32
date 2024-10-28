package ddalpi.attacker.httpRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HttpConcurrentClient {
    private static final int ASYNC_REQUEST_COUNT = 4;
    private final HttpClient client;

    private final Logger logger = Logger.getLogger(HttpConcurrentClient.class.getName());

    public HttpConcurrentClient() {
        this.client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    private List<CompletableFuture<Long>> sendGetRequest(String url, HttpClient client) {
        return IntStream.range(0, ASYNC_REQUEST_COUNT)
                .mapToObj(j -> {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .build();

                    Instant start = Instant.now();

                    return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                            .orTimeout(1, TimeUnit.HOURS)
                            .thenApply(response -> {

                                int statusCode = response.statusCode();

                                if (statusCode < 200 || statusCode >= 400) {
                                    return null;
                                }

                                Instant end = Instant.now();
                                return Duration.between(start, end).toMillis();
                            });

                })
                .collect(Collectors.toList());
    }

    public double getAverageResponseTime(String url) throws InterruptedException, ExecutionException {
        List<CompletableFuture<Long>> futures = sendGetRequest(url, client);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join();

        long totalResponseTime = 0;

        for (CompletableFuture<Long> future : futures) {
            try{
                totalResponseTime += future.get();
            }catch (NullPointerException e){
                totalResponseTime += 1600;
            }
        }
        double averageResponseTime = totalResponseTime / (double) futures.size();

        logger.info(""+averageResponseTime);
        if (averageResponseTime > 2000){
            return 2000;
        }

        return averageResponseTime;
    }
}
