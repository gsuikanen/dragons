package com.example.adventurebot.controller;

import com.example.adventurebot.model.GameState;
import com.example.adventurebot.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public ResponseEntity<GameState> startGame() {
        GameState result = gameService.playGame();
        return ResponseEntity.ok(result);
    }
}