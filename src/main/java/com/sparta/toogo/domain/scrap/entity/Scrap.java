package com.sparta.toogo.domain.scrap.entity;

import com.sparta.toogo.domain.post.entity.Category;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.global.utill.Timestamped;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
public class Scrap extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 스크랩할때 단방향 User, Post를 받아온다
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Enumerated(EnumType.STRING)
    private Category.PostCategory category;

    public Scrap(Post post, User user, Category.PostCategory category) {
        this.user = user;
        this.post = post;
        this.category = category;
    }
}
