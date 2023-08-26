package com.sparta.toogo.domain.comment.dto;

import com.sparta.toogo.domain.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@NoArgsConstructor
@Getter
public class CommentResponseDto {

    private Long id;
    private String comment;
    private String nickname;
    private LocalDateTime createdAt;
    private String emoticon;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.nickname = comment.getUser().getNickname();
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime utcTime = comment.getCreatedAt().atZone(utcZone);
        ZonedDateTime koreaTime = utcTime.withZoneSameInstant(koreaZone);
        this.createdAt = koreaTime.toLocalDateTime();
        this.emoticon = comment.getUser().getEmoticon();
    }

//    public CommentResponseDto(String status, Comment comment) {
//        this.status = status;
//        this.comment = String.valueOf(comment);
//    }
}
