package com.example.adventurebot.service;

import com.example.adventurebot.client.GameApiClient;
import com.example.adventurebot.model.Ad;
import com.example.adventurebot.model.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Service
public class StrategyService {
    private static final Logger log = LoggerFactory.getLogger(StrategyService.class);
    private final GameApiClient apiClient;
    private final Random random = new Random();

    private static final String[] POWER_UPS = {"cs", "gas", "wax", "tricks", "wingpot"};
    private static final String[] MEGA_POWER_UPS = {"ch", "rf", "iron", "mtrix", "wingpotmax"};

    public StrategyService(GameApiClient apiClient) {
        this.apiClient = apiClient;
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

    public void makePurchaseDecision(GameState game) {
        if (game.getGold() >= 400) {
            purchaseRandomItem(game, MEGA_POWER_UPS);
        } else if (game.getGold() >= 150) {
            purchaseRandomItem(game, POWER_UPS);
        }

        if (game.getLives() == 1 && game.getGold() >= 50) {
            purchaseItem(game, "hpot");
        }
    }

    private void purchaseRandomItem(GameState game, String[] items) {
        String randomItem = items[random.nextInt(items.length)];
        purchaseItem(game, randomItem);
    }

    private void purchaseItem(GameState game, String item) {
        var shoppingResult = apiClient.purchaseItem(game.getGameId(), item);
        log.info("Purchase {}: {}", item, shoppingResult);

        game.setGold(shoppingResult.getGold());
        game.setLevel(shoppingResult.getLevel());
        game.setTurn(shoppingResult.getTurn());
        game.setLives(shoppingResult.getLives());
    }
}
