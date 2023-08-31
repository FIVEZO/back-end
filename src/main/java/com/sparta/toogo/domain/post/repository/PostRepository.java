package com.sparta.toogo.domain.post.repository;

import com.sparta.toogo.domain.post.entity.Category;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByCategoryAndId(Category.PostCategory category, Long postId);

    Page<Post> findAllByCategory(Category.PostCategory categoryEnum, Pageable pageable);


    Page<Post> findAllByCategoryAndCountry(Category.PostCategory categoryEnum, String country, Pageable pageable);

    List<Post> findByUser(User user);

    Optional<Post> findByUserId(Long id);
}