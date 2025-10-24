package com.example.adventurebot.model;

public class Ad {

    private String adId;
    private String message;
    private int reward;
    private int expiresIn;
    private String probability;

    public Ad() {}

    public Ad(String adId, String message, int reward, int expiresIn, String probability) {
        this.adId = adId;
        this.message = message;
        this.reward = reward;
        this.expiresIn = expiresIn;
        this.probability = probability;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public boolean isExpired() {
        return expiresIn <= 0;
    }

    public int getProbabilityValue() {
        return switch (probability.toLowerCase()) {
            case "walk in the park" -> 100;
            case "piece of cake" -> 90;
            case "quite likely" -> 80;
            case "rather detrimental" -> 60;
            case "gamble" -> 50;
            default -> 0;
        };
    }

    @Override
    public String toString() {
        return "Ad{" +
                "adId='" + adId + '\'' +
                ", message='" + message + '\'' +
                ", reward=" + reward +
                ", expiresIn=" + expiresIn +
                ", probability='" + probability + '\'' +
                '}';
    }
}
