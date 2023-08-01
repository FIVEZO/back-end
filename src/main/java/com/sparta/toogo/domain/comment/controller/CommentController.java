package com.sparta.toogo.domain.comment.controller;

import com.sparta.toogo.domain.comment.dto.CommentRequestDto;
import com.sparta.toogo.domain.comment.dto.CommentResponseDto;
import com.sparta.toogo.domain.comment.service.CommentService;
import com.sparta.toogo.domain.user.entity.User;
import com.sparta.toogo.global.enums.SuccessCode;
import com.sparta.toogo.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post/{category}/{postId}")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long category,
                                                            @PathVariable Long postId,
                                                            @RequestBody CommentRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto response = commentService.createComment(category, postId, requestDto, userDetails.getUser());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId,
                                                            @RequestBody CommentRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto response = commentService.updateComment(commentId, requestDto, userDetails.getUser());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<String> deleteCommenet(@PathVariable Long commentId,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        SuccessCode successCode = commentService.deleteComment(commentId, userDetails.getUser());
        return new ResponseEntity<>(successCode.getDetail(), successCode.getHttpStatus());
    }
}
