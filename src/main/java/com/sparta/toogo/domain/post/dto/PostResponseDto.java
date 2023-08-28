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
    private String nickname;
    private Long scrapPostSum;    //  스크랩 기능
    private List<CommentResponseDto> commentList;
    private boolean isScrap;
    private String country;
    private double latitude;
    private double longitude;
    private String meetDate;
    private String people;
    private Long category;
    private String newIntroduction;
    private String emoticon;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = processTitle(post.getTitle(), post.getCountry());
        this.contents = post.getContents();
        this.nickname = post.getUser().getNickname();
        this.scrapPostSum = post.getScrapPostSum();
        this.commentList = post.getCommentList().stream().map(CommentResponseDto::new).toList();
//        this.isScrap = false;
        this.category = post.getCategory().getValue();
        this.country = post.getCountry();
        this.latitude = post.getLatitude();
        this.longitude = post.getLongitude();
        this.meetDate = post.getMeetDate();
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime utcTime = post.getCreatedAt().atZone(utcZone);
        ZonedDateTime koreaTime = utcTime.withZoneSameInstant(koreaZone);

        this.createdAt = koreaTime.toLocalDateTime();
        this.people = String.valueOf(post.getPeople());
//        this.newIntroduction = post.getUser().getMyPage().getIntroduction();
        this.emoticon = post.getUser().getEmoticon();
    }

    private String processTitle(String title, String country) {
        if (title == null || country == null) {
            return title;
        }
        return "[" + country + "] " + title;
    }

    public PostResponseDto(Post post, String newIntroduction, boolean isScrap) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.nickname = post.getUser().getNickname();
        this.scrapPostSum = post.getScrapPostSum();
        this.commentList = post.getCommentList().stream().map(CommentResponseDto::new).toList();
        this.isScrap = isScrap;
        this.country = post.getCountry();
        this.latitude = post.getLatitude();
        this.longitude = post.getLongitude();
        this.category = post.getCategory().getValue();
        this.meetDate = post.getMeetDate();
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime utcTime = post.getCreatedAt().atZone(utcZone);
        ZonedDateTime koreaTime = utcTime.withZoneSameInstant(koreaZone);

        this.createdAt = koreaTime.toLocalDateTime();
        this.people = String.valueOf(post.getPeople());
        this.newIntroduction = newIntroduction;
        this.emoticon = post.getUser().getEmoticon();
    }
}

