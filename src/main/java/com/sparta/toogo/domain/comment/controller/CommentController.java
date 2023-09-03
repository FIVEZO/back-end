package com.sparta.toogo.domain.comment.controller;

import com.sparta.toogo.domain.comment.dto.CommentRequestDto;
import com.sparta.toogo.domain.comment.dto.CommentResponseDto;
import com.sparta.toogo.domain.comment.service.CommentService;
import com.sparta.toogo.domain.notification.service.NotificationService;
import com.sparta.toogo.global.enums.SuccessCode;
import com.sparta.toogo.global.responsedto.ApiResponse;
import com.sparta.toogo.global.security.UserDetailsImpl;
import com.sparta.toogo.global.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post/{category}/{postId}")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final NotificationService notificationService;

    @Operation(summary = "댓글 작성", description = "댓글을 작성합니다.")
    @PostMapping("/comment")
    public ApiResponse<CommentResponseDto> createComment(@PathVariable Long category,
                                                         @PathVariable Long postId,
                                                         @RequestBody CommentRequestDto requestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto response = commentService.createComment(category, postId, requestDto, userDetails.getUser());
        notificationService.notifyComment(postId);
        return ResponseUtil.ok(response);
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @PatchMapping("/comment/{commentId}")
    public ApiResponse<CommentResponseDto> updateComment(@PathVariable Long commentId,
                                                         @RequestBody CommentRequestDto requestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto response = commentService.updateComment(commentId, requestDto, userDetails.getUser());
        return ResponseUtil.ok(response);
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @DeleteMapping("/comment/{commentId}")
    public ApiResponse<String> deleteComment(@PathVariable Long commentId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        SuccessCode successCode = commentService.deleteComment(commentId, userDetails.getUser());
        return ResponseUtil.ok(successCode.getDetail());
    }
}
