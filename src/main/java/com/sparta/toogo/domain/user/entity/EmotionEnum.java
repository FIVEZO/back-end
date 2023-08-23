package com.sparta.toogo.domain.user.entity;

public enum EmotionEnum {
    HAPPY("1"),
    ANNOYED("2"),
    BORED("3"),
    SLEEPY("4"),
    SURPRISED("5");

    private final String emotion;

    EmotionEnum(String emotion) {
        this.emotion = emotion;
    }

    public String getEmotion() {
        return this.emotion;
    }
}