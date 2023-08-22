package com.sparta.toogo.domain.mypage.controller;

import com.sparta.toogo.domain.mypage.dto.*;
import com.sparta.toogo.domain.mypage.service.MyPageService;
import com.sparta.toogo.domain.post.dto.PostResponseGetDto;
import com.sparta.toogo.global.responsedto.ApiResponse;
import com.sparta.toogo.global.security.UserDetailsImpl;
import com.sparta.toogo.global.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(summary = "마이페이지 조회")
    @GetMapping("/post")
    public ApiResponse<?> getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtil.ok(myPageService.getMyPage(userDetails.getUser()));
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/delete")
    public MsgResponseDto deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return myPageService.deleteUser(userDetails.getUser());
    }

    @Operation(summary = "내가 스크랩한 게시물 조회")
    @GetMapping("/scrap/{pageNum}")
    public ApiResponse<?> getMyScrap(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @PathVariable int pageNum) {
        return ResponseUtil.ok(myPageService.getMyScrap(userDetails.getUser(), pageNum - 1));
    }

    @Operation(summary = "내 정보 수정")
    @PatchMapping("/update")
    public MyPageResponseDto updateMyPage(@RequestBody MyPageRequestDto requestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return myPageService.updateUser(requestDto, userDetails.getUser());
    }
}
