package com.sparta.toogo.domain.post.dto;

import com.sparta.toogo.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPagePostDto {

    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private Long myScrapCount;

    public MyPagePostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.myScrapCount = 0L; // 초기화 추가
    }

    public MyPagePostDto(Post post, Long myScrapCount) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.myScrapCount = myScrapCount;
    }
}
