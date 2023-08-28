package com.sparta.toogo.domain.home.dto;

import com.sparta.toogo.domain.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
public class HomeResponseDto {

    private Long id;
    private String nickname;
    private String title;
    private String country;
    private String contents;
    private LocalDateTime createdAt;
    private String meetDate;
    private Long category;
    private String people;
    private String emoticon;


    public HomeResponseDto(Post post){
        this.id = post.getId();
        this.nickname = post.getUser().getNickname();
        this.title = processTitle(post.getTitle(), post.getCountry());
        this.country = post.getCountry();
        this.contents = post.getContents();
 //       this.createdAt = post.getCreatedAt();
        this.meetDate = post.getMeetDate();
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
        if(title == null || country == null) {
            return title;
        }
        return "[" + country + "] " + title;
    }
}
