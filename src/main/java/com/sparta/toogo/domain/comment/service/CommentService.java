package com.sparta.toogo.domain.comment.service;

import com.sparta.toogo.domain.comment.dto.CommentRequestDto;
import com.sparta.toogo.domain.comment.dto.CommentResponseDto;
import com.sparta.toogo.domain.comment.entity.Comment;
import com.sparta.toogo.domain.comment.repository.CommentRepository;
import com.sparta.toogo.domain.post.entity.Category;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.sparta.toogo.global.enums.SuccessCodeEnum.DELETE_SUCCESS;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(Long category, Long postId, CommentRequestDto requestDto) {
        Category.PostCategory categoryEnum = Category.findByNumber(category);
        Post post = findPost(categoryEnum, postId);
        Comment comment = new Comment(post, requestDto);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    private Post findPost(Category.PostCategory category, Long postId) {
        return postRepository.findByCategoryAndId(category, postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto) {
        Comment comment = checkComment(commentId);
        comment.update(requestDto);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    public String deleteComment(Long commentId) {
        Comment comment = checkComment(commentId);
        commentRepository.delete(comment);
        return DELETE_SUCCESS.getMessage();
    }

    private Comment checkComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글은 존재하지 않습니다."));
//        if(!(comment.getUser.getId.equals(user.getId))) {
//            throw new UnauthorizedException("작성자만 수정,삭제가 가능합니다");
//        }
        return comment;
    }
}

