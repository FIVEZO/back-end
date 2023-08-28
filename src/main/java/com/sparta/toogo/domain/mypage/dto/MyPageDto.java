package com.sparta.toogo.domain.mypage.dto;

import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
public class MyPageDto {

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

    public MyPageDto(Post post) {

        this.id = post.getId();
        this.nickname = post.getUser().getNickname();
        this.title = post.getTitle();
        this.country = post.getCountry();
        this.contents = post.getContents();
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime utcTime = post.getCreatedAt().atZone(utcZone);
        ZonedDateTime koreaTime = utcTime.withZoneSameInstant(koreaZone);
        this.createdAt = koreaTime.toLocalDateTime();
        this.meetDate = post.getMeetDate();
        this.scrapPostSum  = post.getScrapPostSum();
        this.category = post.getCategory().getValue();
        this.people = String.valueOf(post.getPeople());
        this.emoticon = post.getUser().getEmoticon();
    }

    public MyPageDto(Post post, Long myScrapCount) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime utcTime = post.getCreatedAt().atZone(utcZone);
        ZonedDateTime koreaTime = utcTime.withZoneSameInstant(koreaZone);
        this.createdAt = koreaTime.toLocalDateTime();
        this.nickname = post.getUser().getNickname();
        this.country = post.getCountry();
        this.meetDate = post.getMeetDate();
        this.scrapPostSum  = post.getScrapPostSum();
        this.category = post.getCategory().getValue();
        this.people = String.valueOf(post.getPeople());
        this.emoticon = post.getUser().getEmoticon();
    }

//    public MyPageDto(User user){
//        this.nickname = user.getNickname();
//        this.profileImg = user.getProfileImg();
//
//    }
}
