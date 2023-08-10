package com.sparta.toogo.domain.post.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {

    private String title;
    private String contents;
    private String country;
    private double latitude; // 위도
    private double longitude; // 경도
    private String meetDate;
}
