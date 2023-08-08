package com.sparta.toogo.domain.post.entity;

public class Category {

    public enum PostCategory {
        ASIA(1),

        AFRICA(2),

        EUROPE(3),

        OCEANIA(4),

        AMERICA(5);


        private int value;

        PostCategory(int value) {
            this.value = value;
        }

        public Long getValue() {
            return (long) value;
        }
    }

    public static PostCategory findByNumber(Long num) {
        for (PostCategory postCategory : PostCategory.values()) {
            if (num == postCategory.value) {
                return postCategory;
            }
        }
        throw new IllegalArgumentException("올바른 숫자가 없습니다.");
    }
}
