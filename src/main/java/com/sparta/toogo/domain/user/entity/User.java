package com.sparta.toogo.domain.user.entity;

import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.global.util.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(nullable = false)
    private boolean userStatus = true;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @Column
    private Long kakaoId;

    public void Delete() {
        this.userStatus = false;
    }

    public User(String email, String password, String nickname, UserRoleEnum role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    public User(String email, String password, String nickname, UserRoleEnum role, Long kakaoId) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.kakaoId = kakaoId;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}
