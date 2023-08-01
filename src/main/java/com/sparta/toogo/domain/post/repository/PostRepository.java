package com.sparta.toogo.domain.post.repository;

import com.sparta.toogo.domain.post.entity.Category;
import com.sparta.toogo.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategory(String category);


    List<Post> findAllByCategory(Category.PostCategory categoryEnum);

    Optional<Post> findByCategoryAndId(Category.PostCategory category, Long postId);
}
