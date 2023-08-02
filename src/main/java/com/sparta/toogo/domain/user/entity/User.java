package com.sparta.toogo.domain.user.entity;

import com.sparta.toogo.global.util.Timestamped;
import jakarta.persistence.*;
import lombok.*;

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

    private Long kakaoId;

    public User(String nickname, String password, String email, UserRoleEnum role) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(String nickname, String password, String email, UserRoleEnum role, Long kakaoId) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = role;
        this.kakaoId = kakaoId;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}