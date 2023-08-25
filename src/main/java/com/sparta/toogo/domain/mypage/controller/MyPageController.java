package com.sparta.toogo.domain.mypage.controller;

import com.sparta.toogo.domain.mypage.dto.MsgResponseDto;
import com.sparta.toogo.domain.mypage.dto.MyPagePatchResponseDto;
import com.sparta.toogo.domain.mypage.dto.MyPageRequestDto;
import com.sparta.toogo.domain.mypage.dto.MyPageResponseDto;
import com.sparta.toogo.domain.mypage.service.MyPageService;
import com.sparta.toogo.global.responsedto.ApiResponse;
import com.sparta.toogo.global.security.UserDetailsImpl;
import com.sparta.toogo.global.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(summary = "내가 작성한 게시글 조회")
    @GetMapping("/post")
    public ApiResponse<?> getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtil.ok(myPageService.getMyPagePost(userDetails.getUser()));
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

    @Operation(summary = "닉네임, 소개 수정")
    @PatchMapping("/edituser")
    public MyPagePatchResponseDto myPageUpdate(@RequestBody MyPageRequestDto requestDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return myPageService.myPageUpdate(requestDto, userDetails.getUser());
    }

    @Operation(summary = "비밀번호 수정")
    @PatchMapping("/pwupdate")
    public MyPageResponseDto passwordUpdate(@RequestBody MyPageRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return myPageService.passwordUpdate(requestDto, userDetails.getUser());
    }
}
