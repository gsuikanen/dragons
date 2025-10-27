package com.example.adventurebot.service;

import com.example.adventurebot.model.GameRecord;
import com.example.adventurebot.model.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameHistoryServiceTest {

    private GameHistoryService gameHistoryService;

    @BeforeEach
    void setUp() {
        gameHistoryService = new GameHistoryService();
    }

    private GameState createGameState(String id, int score, int gold, int lives) {
        GameState gameState = new GameState();
        gameState.setGameId(id);
        gameState.setScore(score);
        gameState.setGold(gold);
        gameState.setLives(lives);
        return gameState;
    }

    @Test
    void saveGameResult_ShouldAddNewRecord() {
        GameState game = createGameState("game1", 1000, 200, 3);

        gameHistoryService.saveGameResult(game);
        List<GameRecord> all = gameHistoryService.getAll();
        assertEquals(1, all.size());
        assertEquals(game, all.get(0).getGameState());
        assertNotNull(all.get(0).getTimestamp());
    }

    @Test
    void getLastGames_ShouldReturnMostRecentNRecords_InDescendingOrder() throws InterruptedException {
        GameState game1 = createGameState("game1", 1100, 100, 0);
        GameState game2 = createGameState("game2", 1200, 200, 0);
        gameHistoryService.saveGameResult(game1);
        Thread.sleep(5);
        gameHistoryService.saveGameResult(game2);
        List<GameRecord> last = gameHistoryService.getLastGames(1);
        assertEquals(1, last.size());
        assertEquals("game2", last.get(0).getGameState().getGameId());
    }

    @Test
    void getLastGames_ShouldReturnAllIfNExceedsHistorySize() {
        GameState g1 = createGameState("game1", 1100, 100, 0);
        GameState g2 = createGameState("game2", 1200, 200, 0);
        gameHistoryService.saveGameResult(g1);
        gameHistoryService.saveGameResult(g2);

        List<GameRecord> result = gameHistoryService.getLastGames(5);
        assertEquals(2, result.size());
    }

    @Test
    void getBestGamesByScore_ShouldReturnTopNGamesOrderedByScoreDescending() {
        GameState g1 = createGameState("game1", 1100, 100, 0);
        GameState g2 = createGameState("game2", 1200, 200, 0);
        GameState g3 = createGameState("game3", 1300, 300, 0);

        gameHistoryService.saveGameResult(g1);
        gameHistoryService.saveGameResult(g2);
        gameHistoryService.saveGameResult(g3);

        List<GameRecord> top2 = gameHistoryService.getBestGamesByScore(2);
        assertEquals(2, top2.size());
        assertEquals("game3", top2.get(0).getGameState().getGameId());
        assertEquals("game2", top2.get(1).getGameState().getGameId());
    }

    @Test
    void getLastGames_EmptyList() {
        List<GameRecord> result = gameHistoryService.getLastGames(3);
        assertTrue(result.isEmpty());
    }

    @Test
    void getBestGamesByScore_EmptyList() {
        List<GameRecord> result = gameHistoryService.getBestGamesByScore(3);
        assertTrue(result.isEmpty());
    }
}
