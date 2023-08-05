package com.sparta.toogo.domain.post.dto;

import com.sparta.toogo.domain.comment.dto.CommentResponseDto;
import com.sparta.toogo.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class PostResponseDto {

    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    //    private LocalDateTime modifiedAt;
    private String nickname;
    private Long scrapPostSum;    //  스크랩 기능
    private List<CommentResponseDto> commentList;
    private boolean isScrap;
    private String country;
    private double latitude;
    private double longitude;
    private String meetDate;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = processTitle(post.getTitle(), post.getCountry());
        this.contents = post.getContents();
//        this.createdAt = post.getCreatedAt();
//        this.modifiedAt = post.getModifiedAt();
        this.nickname = post.getUser().getNickname();
        this.scrapPostSum = post.getScrapPostSum();
        this.commentList = post.getCommentList().stream().map(CommentResponseDto::new).toList();
//        this.isScrap = false;
        this.country = post.getCountry();
        this.latitude = post.getLatitude();
        this.longitude = post.getLongitude();
        this.meetDate = post.getMeetDate();
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime utcTime = post.getCreatedAt().atZone(utcZone);
        ZonedDateTime koreaTime = utcTime.withZoneSameInstant(koreaZone);

        this.createdAt = koreaTime.toLocalDateTime();
    }
    //       this.image = "http://localhost:8080/images/" + post.getCountry() + ".jpg";
    // korea.jpg (경복궁)

    private String processTitle(String title, String country) {
        if (title == null || country == null) {
            return title;
        }
        return "[" + country + "]" + title;
    }

    public PostResponseDto(Post post, boolean isScrap) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
//        this.createdAt = post.getCreatedAt();
//        this.modifiedAt = post.getModifiedAt();
        this.nickname = post.getUser().getNickname();
        this.scrapPostSum = post.getScrapPostSum();
        this.commentList = post.getCommentList().stream().map(CommentResponseDto::new).toList();
        this.isScrap = isScrap;
        this.country = post.getCountry();
        this.latitude = post.getLatitude();
        this.longitude = post.getLongitude();
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime utcTime = post.getCreatedAt().atZone(utcZone);
        ZonedDateTime koreaTime = utcTime.withZoneSameInstant(koreaZone);

        this.createdAt = koreaTime.toLocalDateTime();
    }
}

