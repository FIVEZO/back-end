package com.sparta.toogo.domain.post.entity;

import com.sparta.toogo.domain.comment.entity.Comment;
import com.sparta.toogo.domain.post.dto.PostRequestDto;
import com.sparta.toogo.domain.scrap.entity.Scrap;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.global.utill.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
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
    private String country;

    @Column
    private Long scrapPostSum;

    @Enumerated(EnumType.STRING)
    private Category.PostCategory category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Scrap> scrapList;


    public Post(Long category, PostRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.user = user;
//        this.nickname = requestDto.getNickname();
        this.scrapPostSum = 0L;
        this.category = Category.findByNumber(category);
        this.country = requestDto.getCountry();
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    // 포스트 스크랩 수
    public void plusScrapPostSum(){
        this.scrapPostSum += 1;
    }
    public void minusScrapPostSum(){
        this.scrapPostSum -= 1;
    }

}
