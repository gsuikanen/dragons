package com.example.adventurebot.model;

import java.time.LocalDateTime;

public class GameRecord {
    private LocalDateTime timestamp;
    private GameState gameState;

    public GameRecord(LocalDateTime timestamp, GameState gameState) {
        this.timestamp = timestamp;
        this.gameState = gameState;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public GameState getGameState() {
        return gameState;
    }

    @Override
    public String toString() {
        return "GameRecord{" +
                "timestamp=" + timestamp +
                ", gameState=" + gameState +
                '}';
    }
}
