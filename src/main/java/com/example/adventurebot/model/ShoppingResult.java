package com.example.adventurebot.model;

public class ShoppingResult {

    private boolean shoppingSuccess;
    private int gold;
    private int lives;
    private int level;
    private int turn;


    public ShoppingResult() {}

    public ShoppingResult(boolean shoppingSuccess, int gold, int lives, int level, int turn) {
        this.shoppingSuccess = shoppingSuccess;
        this.gold = gold;
        this.lives = lives;
        this.level = level;
        this.turn = turn;
    }

    public boolean isSuccess() {
        return shoppingSuccess;
    }

    public void setShoppingSuccess(boolean shoppingSuccess) {
        this.shoppingSuccess = shoppingSuccess;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public String toString() {
        return "ShoppingResult{" +
                "success='" + shoppingSuccess + '\'' +
                ", gold=" + gold +
                ", lives=" + lives +
                ", level=" + level +
                '}';
    }
}
