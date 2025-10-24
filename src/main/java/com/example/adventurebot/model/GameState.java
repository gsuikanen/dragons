package com.example.adventurebot.model;

public class GameState {

    private String gameId;
    private int lives;
    private int gold;
    private int level;
    private int score;
    private int highScore;
    private int turn;


    public GameState() {}

    public GameState(String gameId, int lives, int gold, int level, int score, int highScore, int turn) {
        this.gameId = gameId;
        this.lives = lives;
        this.gold = gold;
        this.level = level;
        this.score = score;
        this.highScore = highScore;
        this.turn = turn;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public boolean hasLivesLeft() {
        return lives > 0;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "gameId='" + gameId + '\'' +
                ", lives=" + lives +
                ", score=" + score +
                ", gold=" + gold +
                '}';
    }
}
