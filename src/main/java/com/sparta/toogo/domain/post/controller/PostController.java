package com.sparta.toogo.domain.post.controller;

import com.sparta.toogo.domain.post.dto.PostRequestDto;
import com.sparta.toogo.domain.post.dto.PostResponseDto;
import com.sparta.toogo.domain.post.dto.PostResponseGetDto;
import com.sparta.toogo.domain.post.service.PostService;
import com.sparta.toogo.global.responsedto.ApiResponse;
import com.sparta.toogo.global.security.UserDetailsImpl;
import com.sparta.toogo.global.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping("/{category}")
    public ApiResponse<?> createPost(@PathVariable Long category,
                                     @RequestBody PostRequestDto requestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtil.ok(postService.createPost(category, requestDto, userDetails.getUser()));
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<PostResponseGetDto>> getPostsByCategory(@PathVariable Long category,
                                                                    @RequestParam("page") int pageNum) {
        log.info("get 동작중!");
        List<PostResponseGetDto> response = postService.getPostsByCategory(category, pageNum -1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{category}/{country}/list")
    public ApiResponse<List<PostResponseGetDto>> getPostsByCategoryAndCountry(@PathVariable Long category,
                                                                              @PathVariable String country,
                                                                              @RequestParam("page") int pageNum) {
        List<PostResponseGetDto> response = postService.getPostsByCategoryAndCountry(category, country, pageNum -1);
        return ResponseUtil.ok(response);
    }

    @GetMapping("/{category}/{postId}")
    public ResponseEntity<PostResponseDto> getDetailPost(@PathVariable Long category,
                                                      @PathVariable Long postId,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = 0L;
        if(userDetails != null) {
            userId = userDetails.getUser().getId();
        }
        PostResponseDto response = postService.getDetailPost(category, postId, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{category}/{postId}")
    public ApiResponse<?> updatePost(@PathVariable Long category,
                                     @PathVariable Long postId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails,
                                     @RequestBody PostRequestDto requestDto) {
        return ResponseUtil.ok(postService.updatePost(category, postId, userDetails.getUser(), requestDto));
    }

    @DeleteMapping("/{category}/{postId}")
    public ApiResponse<?> deletePost(@PathVariable Long category,
                                     @PathVariable Long postId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtil.ok(postService.deletePost(category, postId, userDetails.getUser()));
    }

    @GetMapping("/{category}/search/{pageNum}")
    public ResponseEntity<List<PostResponseDto>> searchPost(@PathVariable Long category,
                                            @RequestParam String keyword,
                                            @PathVariable int pageNum) {
        return postService.searchPost(keyword, category, pageNum - 1);
    }

}
