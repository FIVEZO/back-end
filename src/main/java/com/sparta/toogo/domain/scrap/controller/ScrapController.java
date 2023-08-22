package com.sparta.toogo.domain.scrap.controller;

import com.sparta.toogo.domain.scrap.dto.ScrapResponseDto;
import com.sparta.toogo.domain.scrap.service.ScrapService;
import com.sparta.toogo.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Slf4j
public class ScrapController {

    private final ScrapService scrapService;

    @Operation(summary = "게시글 스크랩", description = "특정 게시글을 스크랩합니다.")
    @PostMapping("/scrap/{category}/{postId}")
    public ScrapResponseDto scrapPost(@PathVariable Long postId,
                                      @PathVariable Long category,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("post 동작중! = " + 1);
        return scrapService.scrapPost(userDetails.getUser(), postId, category);
    }

}
