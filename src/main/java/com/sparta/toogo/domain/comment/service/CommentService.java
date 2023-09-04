package com.sparta.toogo.domain.comment.service;

import com.sparta.toogo.domain.comment.dto.CommentRequestDto;
import com.sparta.toogo.domain.comment.dto.CommentResponseDto;
import com.sparta.toogo.domain.comment.entity.Comment;
import com.sparta.toogo.domain.comment.exception.CommentException;
import com.sparta.toogo.domain.comment.repository.CommentRepository;
import com.sparta.toogo.domain.post.entity.Category;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.post.exception.PostException;
import com.sparta.toogo.domain.post.repository.PostRepository;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.global.enums.ErrorCode;
import com.sparta.toogo.global.enums.SuccessCode;
import com.sparta.toogo.global.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(Long category, Long postId, CommentRequestDto requestDto, User user) {
        Category.PostCategory categoryEnum = Category.findByNumber(category);
        Post post = findPost(categoryEnum, postId);
        Comment comment = new Comment(post, requestDto, user);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    private Post findPost(Category.PostCategory category, Long postId) {
        return postRepository.findByCategoryAndId(category, postId)
                .orElseThrow(() -> new PostException(ErrorCode.NOT_FOUND_DATA));
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = checkComment(commentId, user);

        if(!(comment.getUser().getId().equals(user.getId()))) {
            throw new UnauthorizedException(ErrorCode.NO_AUTHORITY_TO_DATA);
        }
        comment.update(requestDto);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    public SuccessCode deleteComment(Long commentId, User user) {
        Comment comment = checkComment(commentId, user);
        commentRepository.delete(comment);
        return SuccessCode.COMMENT_DELETE_SUCCESS;
    }

    private Comment checkComment(Long commentId, User user) {
        log.info("commentId = " + commentId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorCode.NOT_FOUND_COMMENT));

        log.info("comment = " + comment.getUser().getId().equals(user.getId()));
        log.info("comment2 = " + comment.getPost().getUser().getId().equals(user.getId()));

        if (comment.getUser().getId().equals(user.getId()) || comment.getPost().getUser().getId().equals(user.getId())) {

        } else {
            throw new UnauthorizedException(ErrorCode.NO_AUTHORITY_TO_DATA);
        }
        return comment;
    }
}

