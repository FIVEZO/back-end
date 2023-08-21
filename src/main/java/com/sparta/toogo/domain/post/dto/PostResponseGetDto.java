package com.sparta.toogo.domain.post.dto;

import com.sparta.toogo.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
    private Long category;
    private Long people;

    public PostResponseGetDto(Post post) {
        this.id = post.getId();
        this.nickname = post.getUser().getNickname();
        this.title = processTitle(post.getTitle(), post.getCountry());
        this.country = post.getCountry();
        this.contents = post.getContents();
        this.meetDate = post.getMeetDate();
        this.scrapPostSum = post.getScrapPostSum();
        this.category = post.getCategory().getValue();
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime utcTime = post.getCreatedAt().atZone(utcZone);
        ZonedDateTime koreaTime = utcTime.withZoneSameInstant(koreaZone);

        this.createdAt = koreaTime.toLocalDateTime();
        this.people = post.getPeople();
    }


    private String processTitle(String title, String country) {
        if (title == null || country == null) {
            return title;
        }
        return "[" + country + "] " + title;
    }
}
