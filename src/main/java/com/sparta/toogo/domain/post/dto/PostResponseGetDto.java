package com.sparta.toogo.domain.post.dto;

import com.sparta.toogo.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private String people;
    private String emoticon;

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
        this.people = String.valueOf(post.getPeople());
        this.emoticon = post.getUser().getEmoticon();
    }


    private String processTitle(String title, String country) {
        if (title == null || country == null) {
            return title;
        }
        return "[" + country + "] " + title;
    }
}
