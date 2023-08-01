package com.sparta.toogo.domain.kakao.entity;

import com.sparta.toogo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "kakao")
public class Kakao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @OneToOne(mappedBy = "user")
    private User user;

    public void userIdUpdate(Long id) {
        this.userId = id;
    }
}