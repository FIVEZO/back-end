package com.sparta.toogo.domain.comment.controller;

import com.sparta.toogo.domain.comment.dto.CommentRequestDto;
import com.sparta.toogo.domain.comment.dto.CommentResponseDto;
import com.sparta.toogo.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post/{category}/{postId}")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long category,
                                                            @PathVariable Long postId,
                                                            @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto response = commentService.createComment(category, postId, requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId,
                                                            @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto response = commentService.updateComment(commentId, requestDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<String> deleteCommenet(@PathVariable Long commentId) {
        String message = commentService.deleteComment(commentId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
