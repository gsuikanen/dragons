package com.example.adventurebot.service;

import com.example.adventurebot.client.GameApiClient;
import com.example.adventurebot.model.Ad;
import com.example.adventurebot.model.AdResult;
import com.example.adventurebot.model.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class GameService {

    private final GameApiClient apiClient;
    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    public GameService(GameApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public GameState playGame() {
        GameState game = apiClient.startGame();
        log.info("Game {}", game);
        String gameId = game.getGameId();
        log.info("Starting game {}", gameId);

        while (game.hasLivesLeft()) {
            List<Ad> ads = apiClient.getAds(gameId);
            log.info("Received {} ads", ads.size());
            Ad best = pickBestAd(ads);
            log.info("Picked ad: {}", best);
            AdResult result = apiClient.solveAd(gameId, best.getAdId());
            log.info("Result: {}", result);
            game.setGold(result.getGold());
            game.setScore(result.getScore());
            game.setLives(result.getLives());
        }

        log.info("Game result: {}", game);

        return game;
    }

    public Ad pickBestAd(List<Ad> ads) {
        return ads.stream()
                .max(Comparator.comparingInt(Ad::getProbabilityValue))
                .map(maxProbAd -> {
                    int maxProb = maxProbAd.getProbabilityValue();
                    return ads.stream()
                            .filter(ad -> ad.getProbabilityValue() == maxProb)
                            .max(Comparator.comparingInt(Ad::getReward))
                            .orElseThrow(() -> new IllegalArgumentException("No ads with max probability"));
                })
                .orElseThrow(() -> new IllegalArgumentException("Ad list is empty"));
    }
}
