package com.sparta.toogo.domain.post.entity;

import com.sparta.toogo.domain.comment.entity.Comment;
import com.sparta.toogo.domain.post.dto.PostRequestDto;
import com.sparta.toogo.global.utill.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column
    private String title;

    @Column
    private String contents;

    @Column
    private String nickname;

    private int scrap;

    @Enumerated(EnumType.STRING)
    private Category.PostCategory category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> commentList = new ArrayList<>();


    public Post(Long category, PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
//        this.nickname = requestDto.getNickname();
//        this.scrap = requestDto.getScrap();
        this.category = Category.findByNumber(category);
    }
}
