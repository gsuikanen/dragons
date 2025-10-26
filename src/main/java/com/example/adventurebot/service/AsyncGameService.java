package com.example.adventurebot.service;

import com.example.adventurebot.model.GameState;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@EnableAsync
@Service
public class AsyncGameService {

    private final GameService gameService;
    private final GameHistoryService historyService;

    public AsyncGameService(GameService gameService, GameHistoryService historyService) {
        this.gameService = gameService;
        this.historyService = historyService;
    }

    @Async
    public CompletableFuture<GameState> playGameAsync(GameState game) {
        GameState result = gameService.playGame(game);
        historyService.saveGameResult(result);
        return CompletableFuture.completedFuture(result);
    }
}
