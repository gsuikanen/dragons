package com.example.adventurebot.model;

public class AdResult {

    private boolean success;
    private int lives;
    private int gold;
    private int score;
    private int highScore;
    private int turn;
    private String message;


    public AdResult() {}

    public AdResult(boolean success, int lives, int gold, int score, int highScore, int turn, String message) {
        this.success = success;
        this.lives = lives;
        this.gold = gold;
        this.score = score;
        this.highScore = highScore;
        this.turn = turn;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {this.message = message;}

    public boolean hasLivesLeft() {
        return lives > 0;
    }

    @Override
    public String toString() {
        return "AdResult{" +
                "success='" + success + '\'' +
                ", lives=" + lives +
                ", score=" + score +
                ", gold=" + gold +
                ", message=" + message +
                '}';
    }
}
