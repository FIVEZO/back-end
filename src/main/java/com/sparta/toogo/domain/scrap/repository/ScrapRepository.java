package com.sparta.toogo.domain.scrap.repository;

import com.sparta.toogo.domain.post.entity.Category;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.scrap.entity.Scrap;
import com.sparta.toogo.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Scrap findByUserAndPostAndCategory(User user, Post post, Category.PostCategory categoryEnum);


    Page<Scrap> findAllByUser(Pageable pageable, User user);


    Optional<Scrap> findByPostIdAndUserId(Long postId, Long userId);

    Long countByUser(User user);
}
