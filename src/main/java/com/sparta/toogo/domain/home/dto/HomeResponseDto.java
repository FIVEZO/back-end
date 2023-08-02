package com.sparta.toogo.domain.home.dto;

import com.sparta.toogo.domain.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HomeResponseDto {

    private Long id;
    private String landmarkImg;
    private String title;
    private String tagname;
    private LocalDateTime createdAt;

    public HomeResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt();
    }
}
