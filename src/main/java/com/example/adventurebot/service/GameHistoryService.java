package com.example.adventurebot.service;

import com.example.adventurebot.model.GameRecord;
import com.example.adventurebot.model.GameState;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameHistoryService {

    private final List<GameRecord> history = new LinkedList<>();

    public void saveGameResult(GameState gameState) {
        history.add(new GameRecord(LocalDateTime.now(), gameState));
    }

    public List<GameRecord> getLastGames(int n) {
        return history.stream()
                .skip(Math.max(0, history.size() - n))
                .sorted(Comparator.comparing(GameRecord::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

    public List<GameRecord> getBestGamesByScore(int n) {
        return history.stream()
                .sorted(Comparator.comparingInt(gr -> -gr.getGameState().getScore()))
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<GameRecord> getAll() {
        return new LinkedList<>(history);
    }
}
