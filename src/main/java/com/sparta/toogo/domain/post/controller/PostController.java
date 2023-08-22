package com.sparta.toogo.domain.post.controller;

import com.sparta.toogo.domain.post.dto.PostRequestDto;
import com.sparta.toogo.domain.post.dto.PostResponseDto;
import com.sparta.toogo.domain.post.service.PostService;
import com.sparta.toogo.global.responsedto.ApiResponse;
import com.sparta.toogo.global.security.UserDetailsImpl;
import com.sparta.toogo.global.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@Api(tags = {"Post API"})
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 작성", description = "게시글을 작성합니다.")
    @PostMapping("/{category}")
    public ApiResponse<?> createPost(@PathVariable Long category,
                                     @RequestBody PostRequestDto requestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtil.ok(postService.createPost(category, requestDto, userDetails.getUser()));
    }

    @Operation(summary = "게시글 전체 조회", description = "게시글 목록을 조회합니다.")
    @GetMapping("/{category}")
    public ResponseEntity<Map<String, Object>> getPostsByCategory(@PathVariable Long category,
                                                                  @RequestParam("page") int pageNum) {
        log.info("get 동작중!");
        Map<String, Object> response = postService.getPostsByCategory(category, pageNum - 1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{category}/{country}/list")
    public ApiResponse<Map<String, Object>> getPostsByCategoryAndCountry(@PathVariable Long category,
                                                                         @PathVariable String country,
                                                                         @RequestParam("page") int pageNum) {
        Map<String, Object> response = postService.getPostsByCategoryAndCountry(category, country, pageNum - 1);
        return ResponseUtil.ok(response);
    }

    @Operation(summary = "게시글 선택 조회", description = "게시글을 조회합니다.")
    @GetMapping("/{category}/{postId}")
    public ResponseEntity<PostResponseDto> getDetailPost(@PathVariable Long category,
                                                         @PathVariable Long postId,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = 0L;
        if (userDetails != null) {
            userId = userDetails.getUser().getId();
        }
        PostResponseDto response = postService.getDetailPost(category, postId, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    @PatchMapping("/{category}/{postId}")
    public ApiResponse<?> updatePost(@PathVariable Long category,
                                     @PathVariable Long postId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails,
                                     @RequestBody PostRequestDto requestDto) {
        return ResponseUtil.ok(postService.updatePost(category, postId, userDetails.getUser(), requestDto));
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    @DeleteMapping("/{category}/{postId}")
    public ApiResponse<?> deletePost(@PathVariable Long category,
                                     @PathVariable Long postId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtil.ok(postService.deletePost(category, postId, userDetails.getUser()));
    }

    @Operation(summary = "게시글 검색", description = "게시글을 검색합니다.")
    @GetMapping("/search/{pageNum}")
    public ResponseEntity<List<PostResponseDto>> searchPost(@RequestParam("keyword") String keyword,
                                                            @PathVariable int pageNum) {
        log.info("keyword = {} ", keyword);
        List<PostResponseDto> response = postService.searchPost(keyword, pageNum - 1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
