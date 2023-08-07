package com.sparta.toogo.domain.comment.entity;

import com.sparta.toogo.domain.comment.dto.CommentRequestDto;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.global.utill.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(Post post, CommentRequestDto requestDto, User user) {
        this.comment = requestDto.getComment();
        this.user = user;
        this.post = post;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
