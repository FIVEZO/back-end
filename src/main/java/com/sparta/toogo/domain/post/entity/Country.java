package com.sparta.toogo.domain.post.entity;

public class Country {
    public enum PostCountry {
        KOREA("Korea"),

        JAPAN("Japan"),

        PARIS("Paris"),

        OCEANIA("4"),

        NORTH_AMERICA("5"),

        SOUTH_AMERICA("6");

        private String value;

        PostCountry(String value) {
            this.value = value;
        }
    }

    public static Country.PostCountry findByStr(String country) {
        for(Country.PostCountry postCountry : Country.PostCountry.values()) {
            if(country.equals(postCountry.value)) {
                return postCountry;
            }
        }
        throw new IllegalArgumentException("올바른 국가가 없습니다.");
    }
}
