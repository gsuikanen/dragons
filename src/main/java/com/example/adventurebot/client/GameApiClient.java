package com.example.adventurebot.client;

import com.example.adventurebot.model.*;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.List;

@Component
public class GameApiClient {

    private final WebClient webClient;

    public GameApiClient(GameApiProperties properties, WebClient.Builder builder) {
        ConnectionProvider provider = ConnectionProvider.builder("game-api")
                .maxConnections(50)
                .maxIdleTime(Duration.ofSeconds(30))
                .maxLifeTime(Duration.ofMinutes(5))
                .build();

        HttpClient httpClient = HttpClient.create(provider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofSeconds(5))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5))
                                .addHandlerLast(new WriteTimeoutHandler(5))
                );

        this.webClient = builder
                .baseUrl(properties.getBaseUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    public GameState startGame() {
        return webClient.post()
                .uri("/game/start")
                .retrieve()
                .bodyToMono(GameState.class)
                .block();
    }

    public List<Ad> getAds(String gameId) {
        return webClient.get()
                .uri("/{gameId}/messages", gameId)
                .retrieve()
                .bodyToFlux(Ad.class)
                .collectList()
                .block();
    }

    public AdResult solveAd(String gameId, String adId) {
        return webClient.post()
                .uri("/{gameId}/solve/{adId}", gameId, adId)
                .retrieve()
                .bodyToMono(AdResult.class)
                .block();
    }

    public List<ShoppingItem> getShoppingItems(String gameId) {
        return webClient.get()
                .uri("/{gameId}/shop", gameId)
                .retrieve()
                .bodyToFlux(ShoppingItem.class)
                .collectList()
                .block();
    }

    public ShoppingResult purchaseItem(String gameId, String itemId) {
        return webClient.post()
                .uri("/{gameId}/shop/buy/{itemId}", gameId, itemId)
                .retrieve()
                .bodyToMono(ShoppingResult.class)
                .block();
    }
}
