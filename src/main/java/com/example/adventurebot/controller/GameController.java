package com.example.adventurebot.controller;

import com.example.adventurebot.model.GameRecord;
import com.example.adventurebot.model.GameState;
import com.example.adventurebot.service.GameHistoryService;
import com.example.adventurebot.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    private final GameHistoryService historyService;

    public GameController(GameService gameService, GameHistoryService historyService) {
        this.gameService = gameService;
        this.historyService = historyService;
    }

    @PostMapping("/start")
    public ResponseEntity<GameState> startGame() {
        GameState result = gameService.playGame();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/last")
    public List<GameRecord> getLastGames(@RequestParam(defaultValue = "5") int limit) {
        return historyService.getLastGames(limit);
    }

    @GetMapping("/best")
    public List<GameRecord> getBestGames(@RequestParam(defaultValue = "5") int limit) {
        return historyService.getBestGamesByScore(limit);
    }
}