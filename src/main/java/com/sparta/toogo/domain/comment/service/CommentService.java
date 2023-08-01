package com.sparta.toogo.domain.comment.service;

import com.sparta.toogo.domain.comment.dto.CommentRequestDto;
import com.sparta.toogo.domain.comment.dto.CommentResponseDto;
import com.sparta.toogo.domain.comment.entity.Comment;
import com.sparta.toogo.domain.comment.repository.CommentRepository;
import com.sparta.toogo.domain.post.entity.Category;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.post.repository.PostRepository;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.global.enums.ErrorCode;
import com.sparta.toogo.global.enums.SuccessCode;
import com.sparta.toogo.global.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
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
                .orElseThrow(() -> new ResponseStatusException(ErrorCode.NOT_FOUND_DATA.getHttpStatus(), ErrorCode.NOT_FOUND_DATA.getDetail()));
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = checkComment(commentId, user);
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
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 댓글은 존재하지 않습니다."));
        if(!(comment.getUser().getId().equals(user.getId()))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "작성자만 수정, 삭제가 가능합니다.");
        }
        return comment;
    }
}

