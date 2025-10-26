package com.example.adventurebot.service;

import com.example.adventurebot.client.GameApiClient;
import com.example.adventurebot.model.Ad;
import com.example.adventurebot.model.AdResult;
import com.example.adventurebot.model.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GameService {

    private final GameApiClient apiClient;
    private final StrategyService strategyService;
    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    public GameService(GameApiClient apiClient, StrategyService strategyService) {
        this.apiClient = apiClient;
        this.strategyService = strategyService;
    }

    public GameState startNewGame() {
        return apiClient.startGame();
    }

    public GameState playGame(GameState game) {
        while (game.hasLivesLeft()) {
            List<Ad> ads = apiClient.getAds(game.getGameId());
            Ad best = strategyService.pickBestAd(ads);
            AdResult result = apiClient.solveAd(game.getGameId(), best.getAdId());
            game.setGold(result.getGold());
            game.setScore(result.getScore());
            game.setLives(result.getLives());
            game.setTurn(result.getTurn());
            strategyService.makePurchaseDecision(game);
        }

        log.info("Game result: {}", game);
        return game;
    }
}
