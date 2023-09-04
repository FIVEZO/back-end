package com.sparta.toogo.domain.post.entity;

import com.sparta.toogo.domain.comment.entity.Comment;
import com.sparta.toogo.domain.messageroom.entity.MessageRoom;
import com.sparta.toogo.domain.notification.entity.Notification;
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
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(length = 1000)
    private String contents;

    @Column
    private String country;

    @Column
    private Long scrapPostSum;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column
    private String meetDate;

    @Column
    private String people;

    @Enumerated(EnumType.STRING)
    private Category.PostCategory category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scrap> scrapList;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MessageRoom> messageRoom;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Notification> notificationList = new ArrayList<>();

    public Post(Long category, PostRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.user = user;
        this.scrapPostSum = 0L;
        this.category = Category.findByNumber(category);
        this.country = requestDto.getCountry();
        this.latitude = requestDto.getLatitude();
        this.longitude = requestDto.getLongitude();
        this.meetDate = requestDto.getMeetDate();
        this.people = requestDto.getPeople();
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.meetDate = requestDto.getMeetDate();
        this.people = requestDto.getPeople();
    }

    // 포스트 스크랩 수
    public void plusScrapPostSum() {
        this.scrapPostSum += 1;
    }

    public void minusScrapPostSum() {
        this.scrapPostSum -= 1;
    }
}
