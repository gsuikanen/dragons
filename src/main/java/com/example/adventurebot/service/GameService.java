package com.example.adventurebot.service;

import com.example.adventurebot.client.GameApiClient;
import com.example.adventurebot.model.Ad;
import com.example.adventurebot.model.AdResult;
import com.example.adventurebot.model.GameState;
import com.example.adventurebot.model.ShoppingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Service
public class GameService {

    private final GameApiClient apiClient;
    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    public GameService(GameApiClient apiClient) {
        this.apiClient = apiClient;
    }



    public GameState playGame() {
        GameState game = apiClient.startGame();
        String gameId = game.getGameId();

        String[] powerUps = {"cs", "gas", "wax", "tricks", "wingpot"};
        String[] megaPowerUps = {"ch", "rf", "iron", "mtrix", "wingpotmax"};
        Random rand = new Random();

        while (game.hasLivesLeft()) {
            List<Ad> ads = apiClient.getAds(gameId);
            Ad best = pickBestAd(ads);
            AdResult result = apiClient.solveAd(gameId, best.getAdId());
            game.setGold(result.getGold());
            game.setScore(result.getScore());
            game.setLives(result.getLives());
            game.setTurn(result.getTurn());

            if (game.getGold() >= 400) {
                String randomItem = megaPowerUps[rand.nextInt(megaPowerUps.length)];
                ShoppingResult shoppingResult = apiClient.purchaseItem(gameId, randomItem);
                log.info("Purchase {}: {}", randomItem, shoppingResult);
                game.setGold(shoppingResult.getGold());
                game.setLevel(shoppingResult.getLevel());
                game.setTurn(shoppingResult.getTurn());
            } else if (game.getGold() >= 150) {
                String randomItem = powerUps[rand.nextInt(powerUps.length)];
                ShoppingResult shoppingResult = apiClient.purchaseItem(gameId, randomItem);
                log.info("Purchase {}: {}", randomItem, shoppingResult);
                game.setGold(shoppingResult.getGold());
                game.setLevel(shoppingResult.getLevel());
                game.setTurn(shoppingResult.getTurn());
            }

            if (game.getLives() == 1 && game.getGold() >= 50) {
                ShoppingResult shoppingResult = apiClient.purchaseItem(gameId, "hpot");
                log.info("Purchase hpot: {}", shoppingResult);
                game.setGold(shoppingResult.getGold());
                game.setLives(shoppingResult.getLives());
                game.setTurn(shoppingResult.getTurn());
            }
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
