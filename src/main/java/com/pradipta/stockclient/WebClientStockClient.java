package com.pradipta.stockclient;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;

@NoArgsConstructor
@Log4j2
public class WebClientStockClient {
    private WebClient webClient;

    public WebClientStockClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<StockPrice> pricesFor(String symbol) {
         return webClient.get()
                .uri("http://localhost:8080/stocks/"+symbol)
                .retrieve()
                .bodyToFlux(StockPrice.class)
                .retryBackoff(5, Duration.ofSeconds(1), Duration.ofSeconds(20))
                .doOnError(IOException.class, e->log.error(e.getMessage()));
    }
}
