package com.sparta.toogo.domain.scrap.dto;

import lombok.Getter;

@Getter
public class ScrapResponseDto {
    private boolean IsScrapPost;
    private Long scrapPostSum;
    private String msg;

    public ScrapResponseDto(boolean isScrapPost, Long scrapPostSum, String msg) {
        IsScrapPost = isScrapPost;
        this.scrapPostSum = scrapPostSum;
        this.msg = msg;
    }
}
