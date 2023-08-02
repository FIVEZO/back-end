package com.sparta.toogo.domain.user.entity;

import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.global.util.Timestamped;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
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

    @Column(nullable = false)
    private boolean userStatus = true;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public void Delete() {
        this.userStatus = false;
    }

    public void kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
    }
}
