package com.sparta.toogo.domain.post.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {

    private String title;
    private String contents;
    private String country;
    private double latitude;
    private double longitude;
}
