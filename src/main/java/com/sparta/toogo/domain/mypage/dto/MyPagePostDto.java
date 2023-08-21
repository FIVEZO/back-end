package com.sparta.toogo.domain.mypage.dto;

import com.sparta.toogo.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
public class MyPagePostDto {

    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private String nickname;
//    private Long myScrapCount;

    public MyPagePostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime utcTime = post.getCreatedAt().atZone(utcZone);
        ZonedDateTime koreaTime = utcTime.withZoneSameInstant(koreaZone);

        this.createdAt = koreaTime.toLocalDateTime();
//        this.myScrapCount = 0L; // 초기화 추가
    }

    public MyPagePostDto(Post post, Long myScrapCount) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime utcTime = post.getCreatedAt().atZone(utcZone);
        ZonedDateTime koreaTime = utcTime.withZoneSameInstant(koreaZone);

        this.createdAt = koreaTime.toLocalDateTime();
        this.nickname = post.getUser().getNickname();
//        this.myScrapCount = myScrapCount;
    }
}
