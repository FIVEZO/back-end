package com.sparta.toogo.domain.mypage.entity;

import com.sparta.toogo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MyPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 70)
    private String introduction;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void update(String introduction) {
        this.introduction = introduction;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIntroduction() {
        return this.introduction;
    }
}
