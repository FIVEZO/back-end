package com.sparta.toogo.domain.mypage.controller;

import com.sparta.toogo.domain.mypage.dto.MsgResponseDto;
import com.sparta.toogo.domain.mypage.service.MyPageService;
import com.sparta.toogo.domain.post.dto.MyPagePostDto;
import com.sparta.toogo.domain.post.dto.PostResponseDto;
import com.sparta.toogo.global.responsedto.ApiResponse;
import com.sparta.toogo.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping
    public ApiResponse<?> getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return myPageService.getMyPage(userDetails.getUser());
    }

    @DeleteMapping("/{loginId}")
    public MsgResponseDto deleteUser(@PathVariable Long loginId,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return myPageService.deleteUser(loginId, userDetails.getUser());
    }

    @GetMapping("/scrap/{pageNum}")
    public List<MyPagePostDto> getMyScrap(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @PathVariable int pageNum) {
        return myPageService.getMyScrap(userDetails.getUser(), pageNum - 1);
    }
}
