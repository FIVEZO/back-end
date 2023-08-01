package com.sparta.toogo.domain.post.entity;

public class Category {

    public enum PostCategory {
        ASIA(1),

        AFRICA(2),

        EUROPE(3),

        OCEANIA(4),

        NORTH_AMERICA(5),

        SOUTH_AMERICA(6);

        private int value;

        PostCategory(int value) {
            this.value = value;
        }
    }

    public static PostCategory findByNumber(Long num) {
        for(PostCategory postCategory : PostCategory.values()) {
            if(num == postCategory.value) {
                return postCategory;
            }
        }
        throw new IllegalArgumentException("올바른 숫자가 없습니다.");
    }
}
