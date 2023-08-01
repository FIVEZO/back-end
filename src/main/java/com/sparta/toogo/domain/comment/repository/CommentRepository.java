package com.sparta.toogo.domain.comment.repository;

import com.sparta.toogo.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
