package com.example.adventurebot.service;

import com.example.adventurebot.client.GameApiClient;
import com.example.adventurebot.model.Ad;
import com.example.adventurebot.model.GameState;
import com.example.adventurebot.model.ShoppingResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StrategyServiceTest {

    private GameApiClient apiClient;
    private StrategyService strategyService;

    @BeforeEach
    void setUp() {
        apiClient = mock(GameApiClient.class);
        strategyService = new StrategyService(apiClient);
    }

    @Test
    void pickBestAd_returnsHighestProbabilityAndReward() {
        Ad ad1 = new Ad();
        ad1.setProbability("piece of cake");
        ad1.setReward(50);

        Ad ad2 = new Ad();
        ad2.setProbability("piece of cake");
        ad2.setReward(70);

        Ad ad3 = new Ad();
        ad3.setProbability("suicide mission");
        ad3.setReward(100);

        Ad best = strategyService.pickBestAd(List.of(ad1, ad2, ad3));
        assertEquals(ad2, best);
    }

    @Test
    void makePurchaseDecision_buysMegaPowerUp() {
        GameState game = new GameState();
        game.setGold(400);
        game.setGameId("game1");

        ShoppingResult result = new ShoppingResult();
        result.setGold(100);
        result.setLevel(2);
        result.setTurn(10);

        when(apiClient.purchaseItem(eq("game1"), any())).thenReturn(result);
        strategyService.makePurchaseDecision(game);

        verify(apiClient, atLeastOnce()).purchaseItem(eq("game1"), any());
        assertEquals(100, game.getGold());
        assertEquals(2, game.getLevel());
        assertEquals(10, game.getTurn());
    }

    @Test
    void makePurchaseDecision_buysPowerUp() {
        GameState game = new GameState();
        game.setGold(200);
        game.setGameId("game1");

        ShoppingResult result = new ShoppingResult();
        result.setGold(100);
        result.setLevel(2);
        result.setTurn(10);

        when(apiClient.purchaseItem(eq("game1"), any())).thenReturn(result);
        strategyService.makePurchaseDecision(game);

        verify(apiClient, atLeastOnce()).purchaseItem(eq("game1"), any());
        assertEquals(100, game.getGold());
        assertEquals(2, game.getLevel());
        assertEquals(10, game.getTurn());
    }

    @Test
    void makePurchaseDecision_buysHealthPotion() {
        GameState game = new GameState();
        game.setGameId("game1");
        game.setGold(100);
        game.setLives(1);

        ShoppingResult result = new ShoppingResult();
        result.setGold(50);
        result.setLevel(2);
        result.setTurn(5);
        result.setLives(2);

        when(apiClient.purchaseItem("game1", "hpot")).thenReturn(result);
        strategyService.makePurchaseDecision(game);

        verify(apiClient).purchaseItem("game1", "hpot");
        assertEquals(50, game.getGold());
        assertEquals(2, game.getLives());
    }

    @Test
    void makePurchaseDecision_doesNothing() {
        GameState game = new GameState();
        game.setGameId("game1");
        game.setGold(20);
        game.setLives(3);

        strategyService.makePurchaseDecision(game);
        verify(apiClient, never()).purchaseItem(any(), any());
    }
}
