package com.sparta.toogo.domain.comment.service;

import com.sparta.toogo.domain.comment.dto.CommentRequestDto;
import com.sparta.toogo.domain.comment.dto.CommentResponseDto;
import com.sparta.toogo.domain.comment.entity.Comment;
import com.sparta.toogo.domain.comment.entity.Notification;
import com.sparta.toogo.domain.comment.entity.NotificationType;
import com.sparta.toogo.domain.comment.exception.CommentException;
import com.sparta.toogo.domain.comment.repository.CommentRepository;
import com.sparta.toogo.domain.comment.repository.NotificationRepository;
import com.sparta.toogo.domain.post.entity.Category;
import com.sparta.toogo.domain.post.entity.Post;
import com.sparta.toogo.domain.post.exception.PostException;
import com.sparta.toogo.domain.post.repository.PostRepository;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.global.enums.ErrorCode;
import com.sparta.toogo.global.enums.SuccessCode;
import com.sparta.toogo.global.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final NotificationRepository notificationRepository;

    public CommentResponseDto createComment(Long category, Long postId, CommentRequestDto requestDto, User user) {
        Category.PostCategory categoryEnum = Category.findByNumber(category);
        Post post = findPost(categoryEnum, postId);
        Comment comment = new Comment(post, requestDto, user);
        commentRepository.save(comment);

        // 댓글 작성자가 게시글 작성자가 아닐 때 알림 전송
        if (!Objects.equals(user.getId(), post.getUser().getId())) {
            String content = user.getNickname() + "님이 당신의 게시글에 댓글을 남겼습니다.";
            System.out.println("content = " + content);
     //       String url = "/" + post.getCategory() + "List/" + post.getCategory() + "/" + "detail/" + post.getId();

            // Notification 객체 생성 및 저장
            Notification notification = new Notification(post.getUser(), NotificationType.REPLY, content);
            notificationRepository.save(notification);
        }

        return new CommentResponseDto(comment);
    }

//    public List<CommentResponseDto> getComment(Long postId, User user) {
//        List<Comment> comments = commentRepository.findByPostIdAndUserOrderByCreatedAtDesc(postId, user);
//        return comments.stream()
//                .map(CommentResponseDto::new)
//                .collect(Collectors.toList());
//    }

    private Post findPost(Category.PostCategory category, Long postId) {
        return postRepository.findByCategoryAndId(category, postId)
                .orElseThrow(() -> new PostException(ErrorCode.NOT_FOUND_DATA));
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
                .orElseThrow(() -> new CommentException(ErrorCode.NOT_FOUND_COMMENT));
        if(!(comment.getUser().getId().equals(user.getId()))) {
            throw new UnauthorizedException(ErrorCode.NO_AUTHORITY_TO_DATA);
        }
        return comment;
    }
}

