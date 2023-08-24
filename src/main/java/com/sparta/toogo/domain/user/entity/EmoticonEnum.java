package com.sparta.toogo.domain.user.entity;

public enum EmoticonEnum {
    HAPPY("1"),
    ANNOYED("2"),
    BORED("3"),
    SLEEPY("4"),
    SURPRISED("5");

    private final String emoticon;

    EmoticonEnum(String emoticon) {
        this.emoticon = emoticon;
    }

    public String getEmoticon() {
        return this.emoticon;
    }
}