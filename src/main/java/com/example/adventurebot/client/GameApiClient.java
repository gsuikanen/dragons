package com.example.adventurebot.client;

import com.example.adventurebot.model.Ad;
import com.example.adventurebot.model.AdResult;
import com.example.adventurebot.model.GameState;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class GameApiClient {

    private final WebClient webClient;

    public GameApiClient(GameApiProperties properties, WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl(properties.getBaseUrl())
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
}
