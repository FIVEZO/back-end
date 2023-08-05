package com.sparta.toogo.domain.post.dto;

import com.sparta.toogo.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseGetDto {

    private Long id;
    private String nickname;
    private String title;
    private String country;
    private String contents;
    private LocalDateTime createdAt;
    private String meetDate;
    private Long scrapPostSum;

    public PostResponseGetDto(Post post) {
        this.id = post.getId();
        this.nickname = post.getUser().getNickname();
        this.title = processTitle(post.getTitle(), post.getCountry());
        this.country = post.getCountry();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.meetDate = post.getMeetDate();
        this.scrapPostSum = post.getScrapPostSum();

    }

    private String processTitle(String title, String country) {
        if (title == null || country == null) {
            return title;
        }
        return "[" + country + "]" + title;
    }
}
