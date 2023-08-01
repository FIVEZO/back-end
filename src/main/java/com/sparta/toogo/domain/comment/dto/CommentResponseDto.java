package com.sparta.toogo.domain.comment.dto;

import com.sparta.toogo.domain.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
public class CommentResponseDto {

    private Long id;
    private String comment;
    private String username;
    private LocalDateTime createdAt;
//    private String status;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
//        this.username = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
    }

//    public CommentResponseDto(String status, Comment comment) {
//        this.status = status;
//        this.comment = String.valueOf(comment);
//    }
}
