package com.pradipta.stockclient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


class WebClientStockClientIntegrationTest {
    private WebClient webClient = WebClient.builder().build();

    @Test
    void shouldGetPricesFromTheServer() {
        WebClientStockClient webClientStockClient = new WebClientStockClient(webClient);
        Flux<StockPrice> stockPriceFlux = webClientStockClient.pricesFor("SYMBOL");

        Assertions.assertNotNull(stockPriceFlux);
        Flux<StockPrice> fivePrices = stockPriceFlux.take(5);
        Assertions.assertTrue(fivePrices.count().block() == 5);
    }
}