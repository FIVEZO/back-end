package com.sparta.toogo.domain.comment.repository;

import com.sparta.toogo.domain.comment.entity.Comment;
import com.sparta.toogo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPostIdAndUserOrderByCreatedAtDesc(Long postId, User user);
}
