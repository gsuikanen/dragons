package com.example.adventurebot.controller;

import com.example.adventurebot.model.GameRecord;
import com.example.adventurebot.model.GameState;
import com.example.adventurebot.service.AsyncGameService;
import com.example.adventurebot.service.GameHistoryService;
import com.example.adventurebot.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    private final GameHistoryService historyService;
    private final AsyncGameService asyncGameService;
    private final Map<String, CompletableFuture<GameState>> tasks = new ConcurrentHashMap<>();


    public GameController(GameService gameService, GameHistoryService historyService, AsyncGameService asyncGameService) {
        this.gameService = gameService;
        this.historyService = historyService;
        this.asyncGameService = asyncGameService;
    }

    @PostMapping("/start")
    public Map<String, String> startGame() {
        GameState game = gameService.startNewGame();
        CompletableFuture<GameState> task = asyncGameService.playGameAsync(game);
        tasks.put(game.getGameId(), task);
        return Map.of("gameId", game.getGameId(), "status", "started");
    }

    @GetMapping("/status/{gameId}")
    public Map<String, Object> getStatus(@PathVariable String gameId) {
        CompletableFuture<GameState> task = tasks.get(gameId);

        if (task == null) {
            return Map.of("status", "not_found");
        } else if (task.isDone()) {
            try {
                return Map.of("status", "finished", "result", task.get());
            } catch (Exception e) {
                return Map.of("status", "error", "message", e.getMessage());
            }
        } else {
            return Map.of("status", "running");
        }
    }

    @GetMapping("/stats/last")
    public List<GameRecord> getLastGames(@RequestParam(defaultValue = "5") int limit) {
        return historyService.getLastGames(limit);
    }

    @GetMapping("/stats/best")
    public List<GameRecord> getBestGames(@RequestParam(defaultValue = "5") int limit) {
        return historyService.getBestGamesByScore(limit);
    }
}