package com.sparta.toogo.domain.post.dto;

import com.sparta.toogo.domain.comment.dto.CommentResponseDto;
import com.sparta.toogo.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {

    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String nickname;
    private Long scrapPostSum;    //  스크랩 기능
    private List<CommentResponseDto> commentList;
    private boolean isScrap;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.nickname = post.getUser().getNickname();
        this.scrapPostSum = post.getScrapPostSum();
        this.commentList = post.getCommentList().stream().map(CommentResponseDto::new).toList();
        this.isScrap = false;
 //       this.image = "http://localhost:8080/images/" + post.getCountry() + ".jpg";
        // korea.jpg (경복궁)
    }

    public PostResponseDto(Post post, long scrapPostSum) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.nickname = post.getUser().getNickname();
        this.scrapPostSum = scrapPostSum;
        this.commentList = post.getCommentList().stream().map(CommentResponseDto::new).toList();
    }

    public PostResponseDto(Post post, boolean isScrap) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.nickname = post.getUser().getNickname();
        this.scrapPostSum = post.getScrapPostSum();
        this.isScrap = isScrap;
    }
}
