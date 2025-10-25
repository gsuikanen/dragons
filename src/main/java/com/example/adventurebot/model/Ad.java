package com.example.adventurebot.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Base64;

public class Ad {

    private String adId;
    private String message;
    private int reward;
    private int expiresIn;
    private int encrypted;
    private String probability;

    public Ad() {}

    @JsonCreator
    public Ad(
            @JsonProperty("adId") String adId,
            @JsonProperty("message") String message,
            @JsonProperty("reward") int reward,
            @JsonProperty("expiresIn") int expiresIn,
            @JsonProperty("encrypted") int encrypted,
            @JsonProperty("probability") String probability
    ) {
        this.encrypted = encrypted;
        this.adId = decode(adId);
        this.message = decode(message);
        this.reward = reward;
        this.expiresIn = expiresIn;
        this.probability = decode(probability);
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

    public boolean isEncrypted() {
        return encrypted > 0;
    }

    public int getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(int encrypted) {
        this.encrypted = encrypted;
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
            case "piece of cake" -> 100;
            case "walk in the park" -> 90;
            case "sure thing" -> 80;
            case "quite likely" -> 70;
            case "hmmm...." -> 60;
            case "gamble" -> 50;
            case "risky" -> 40;
            case "playing with fire" -> 30;
            case "rather detrimental" -> 20;
            case "suicide mission" -> 10;
            case "impossible" -> 5;
            default -> 0;
        };
    }

    private String decode(String value) {
        return switch (encrypted) {
            case 1 -> decodeBase64(value);
            case 2 -> decodeRot13(value);
            default -> value;
        };
    }

    private String decodeBase64(String value) {
        return new String(Base64.getDecoder().decode(value));
    }

    private String decodeRot13(String value) {
        StringBuilder result = new StringBuilder();
        for (char c : value.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                result.append((char) ('a' + (c - 'a' + 13) % 26));
            } else if (c >= 'A' && c <= 'Z') {
                result.append((char) ('A' + (c - 'A' + 13) % 26));
            } else {
                result.append(c);
            }
        }
        return result.toString();
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
