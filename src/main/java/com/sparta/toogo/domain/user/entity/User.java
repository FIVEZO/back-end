package com.sparta.toogo.domain.user.entity;

import com.sparta.toogo.domain.comment.entity.Comment;
import com.sparta.toogo.domain.mypage.entity.MyPage;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.global.util.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(nullable = false)
    private String emoticon;

    @Column
    private Long kakaoId;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private MyPage myPage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public User(String email, String password, String nickname, UserRoleEnum role, String emoticon) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.emoticon = emoticon;
    }

    public User(String email, String password, String nickname, UserRoleEnum role, Long kakaoId, String emoticon) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.kakaoId = kakaoId;
        this.emoticon = emoticon;
    }

    public void updatePassword(User user, String newPassword) {
        this.email = user.getEmail();
        this.password = newPassword;
        this.role = user.getRole();
        this.kakaoId = user.getKakaoId();
    }

    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void updatePw(String newPassword) {
        this.password = newPassword;
    }

    public void updateEmoticon(String newEmoticon) {
        this.emoticon = newEmoticon;
    }

    public void setId(long testId) {
        this.id = testId;
    }

    public void setMyPage(MyPage testMyPage) {
        this.myPage = testMyPage;
    }
}